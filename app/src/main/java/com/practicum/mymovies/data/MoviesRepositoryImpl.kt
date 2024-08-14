package com.practicum.mymovies.data

import com.practicum.mymovies.data.converters.MoviesCastConverter
import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchRequest
import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchResponse
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsRequest
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsResponse
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastRequest
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastResponse
import com.practicum.mymovies.domain.api.MoviesRepository
import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieCast
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.util.LocalStorage
import com.practicum.mymovies.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MoviesCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                val stored = localStorage.getSavedFavorites()

                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            inFavorite = stored.contains(it.id))
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
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

}