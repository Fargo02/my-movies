package com.practicum.mymovies.data.dto.moviesCast

data class OtherResponse(
    val items: List<CastItemResponse>,
    val job: String
)