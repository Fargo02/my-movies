package com.practicum.mymovies.domain.api

import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieDetails

interface MoviesInteractor {

    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun searchMoviesDetails(movieId: String, consumer: MoviesDetailsConsumer)

    interface MoviesDetailsConsumer {
        fun consume(foundMoviesDetails: MovieDetails?, errorMessage: String?)
    }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

}