package com.practicum.mymovies.data.dto.moviesCast

import com.practicum.mymovies.data.dto.Response

class MoviesFullCastResponse(
    val actors: List<ActorResponse>,
    val directors: DirectorsResponse,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val others: List<OtherResponse>,
    val title: String,
    val type: String,
    val writers: WritersResponse,
    val year: String
) : Response()