package com.practicum.mymovies.domain.db

import com.practicum.mymovies.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {

    fun historyMovies(): Flow<List<Movie>>
}