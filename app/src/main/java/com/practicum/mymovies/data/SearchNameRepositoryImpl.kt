package com.practicum.mymovies.data

import com.practicum.mymovies.data.dto.nameSearch.NameSearchRequest
import com.practicum.mymovies.data.dto.nameSearch.NameSearchResponse
import com.practicum.mymovies.domain.api.SearchNameRepository
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.Resource

class SearchNameRepositoryImpl(
    private val networkClient: NetworkClient,
): SearchNameRepository {
    override fun searchName(expression: String): Resource<List<Person>> {
        val response = networkClient.doRequest(NameSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as NameSearchResponse) {
                    Resource.Success(results.map {
                        Person(
                            it.id,
                            it.image,
                            it.title,
                            it.description,
                        )
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}