package com.practicum.mymovies.domain.impl

import com.practicum.mymovies.domain.api.SearchNameInteractor
import com.practicum.mymovies.domain.api.SearchNameRepository
import com.practicum.mymovies.util.Resource
import java.util.concurrent.Executors

class SearchNameInteractorImpl(
    private val repository: SearchNameRepository
): SearchNameInteractor {

    private val executer = Executors.newCachedThreadPool()

    override fun searchName(expression: String, consumer: SearchNameInteractor.NameConsumer) {
        executer.execute{
            when(val resource = repository.searchName(expression)){
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }

            }
        }
    }
}