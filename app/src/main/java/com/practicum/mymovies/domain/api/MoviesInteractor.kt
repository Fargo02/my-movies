package com.practicum.mymovies.domain.api

import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieCast
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {

    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun searchMoviesDetails(movieId: String, consumer: MoviesDetailsConsumer)

    interface MoviesDetailsConsumer {
        fun consume(foundMoviesDetails: MovieDetails?, errorMessage: String?)
    }

    fun getMoviesCast(movieId: String, consumer: MoviesFullCastConsumer)

    interface MoviesFullCastConsumer {
        fun consume(foundMoviesCast: MovieCast?, errorMessage: String?)
    }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

}