package com.practicum.mymovies.data.dto.moviesCast

data class DirectorsResponse(
    val items: List<CastItemResponse>,
    val job: String
)