package com.practicum.mymovies.data.dto.moviesSearch

data class MovieDto(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String
)