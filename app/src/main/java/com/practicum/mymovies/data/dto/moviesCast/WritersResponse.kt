package com.practicum.mymovies.data.dto.moviesCast

data class WritersResponse(
    val items: List<CastItemResponse>,
    val job: String
)