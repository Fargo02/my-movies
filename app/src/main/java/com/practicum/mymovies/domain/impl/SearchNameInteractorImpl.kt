package com.practicum.mymovies.domain.impl

import com.practicum.mymovies.domain.api.SearchNameInteractor
import com.practicum.mymovies.domain.api.SearchNameRepository
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class SearchNameInteractorImpl(
    private val repository: SearchNameRepository
): SearchNameInteractor {

    override fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchName(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}