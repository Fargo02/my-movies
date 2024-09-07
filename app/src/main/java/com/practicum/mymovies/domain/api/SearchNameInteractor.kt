package com.practicum.mymovies.domain.api

import com.practicum.mymovies.domain.models.Person

interface SearchNameInteractor {

    fun searchName(expression: String, consumer: NameConsumer)

    interface NameConsumer {
        fun consume(foundPeople: List<Person>?, errorMessage: String?)
    }

}