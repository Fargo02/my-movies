package com.practicum.mymovies.data.dto.nameSearch

import com.practicum.mymovies.data.dto.Response

data class NameSearchResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<NameSearchDto>,
    val searchType: String
) : Response()