package com.practicum.mymovies.presentation.poster.models

import com.practicum.mymovies.domain.models.MovieDetails

sealed interface AboutState {
    data class Content(
        val movie: MovieDetails
    ) : AboutState

    data class Error(
        val message: String
    ) : AboutState
}