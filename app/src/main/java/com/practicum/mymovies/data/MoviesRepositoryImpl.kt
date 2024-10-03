package com.practicum.mymovies.data

import com.practicum.mymovies.data.converters.MovieDbConverter
import com.practicum.mymovies.data.converters.MoviesCastConverter
import com.practicum.mymovies.data.db.AppDatabase
import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchRequest
import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchResponse
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsRequest
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsResponse
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastRequest
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastResponse
import com.practicum.mymovies.data.dto.moviesSearch.MovieDto
import com.practicum.mymovies.domain.api.MoviesRepository
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieCast
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.util.LocalStorage
import com.practicum.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MoviesCastConverter,
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequestSuspend(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))

            200 -> {
                val stored = localStorage.getSavedFavorites()
                with(response as MoviesSearchResponse) {
                    val data = results.map {
                        Movie(
                            id = it.id,
                            resultType = it.resultType,
                            image = it.image,
                            title = it.title,
                            description = it.description,
                            inFavorite = stored.contains(it.id),
                        )
                    }
                    saveMovie(results)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun searchMoviesDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MoviesDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MoviesDetailsResponse) {
                    Resource.Success(MovieDetails(movieId, title, imDbRating, year,
                        countries, genres, directors, writers, stars, plot))
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun searchFullCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MoviesFullCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success(
                    data = movieCastConverter.convert(response as MoviesFullCastResponse)
                )
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    private suspend fun saveMovie(movies: List<MovieDto>) {
        val movieEntities = movies.map { movie -> movieDbConvertor.map(movie) }
        appDatabase.movieDao().insertMovies(movieEntities)
    }

}