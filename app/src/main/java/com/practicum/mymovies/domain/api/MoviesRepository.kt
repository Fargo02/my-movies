package com.practicum.mymovies.domain.api

import com.practicum.mymovies.domain.models.Movie
import com.practicum.mymovies.domain.models.MovieCast
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>

    fun searchMoviesDetails(movieId: String): Resource<MovieDetails>

    fun searchFullCast(movieId: String): Resource<MovieCast>

    fun addMovieToFavorites(movie: Movie)

    fun removeMovieFromFavorites(movie: Movie)

}