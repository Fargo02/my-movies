package com.practicum.mymovies.domain.api

import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchNameRepository {

    fun searchName(expression: String): Flow<Resource<List<Person>>>

}