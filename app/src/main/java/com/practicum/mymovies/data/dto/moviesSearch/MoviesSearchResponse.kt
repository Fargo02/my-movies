package com.practicum.mymovies.data.dto.moviesSearch

import com.practicum.mymovies.data.dto.Response


data class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
) : Response()