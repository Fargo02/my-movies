package com.practicum.mymovies.data

import com.practicum.mymovies.data.dto.nameSearch.NameSearchRequest
import com.practicum.mymovies.data.dto.nameSearch.NameSearchResponse
import com.practicum.mymovies.domain.api.SearchNameRepository
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchNameRepositoryImpl(
    private val networkClient: NetworkClient,
): SearchNameRepository {
    override fun searchName(expression: String): Flow<Resource<List<Person>>> = flow{
        val response = networkClient.doRequestSuspend(NameSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {
                with(response as NameSearchResponse) {
                    val data = results.map {
                        Person(
                            id = it.id,
                            image = it.image,
                            title = it.title,
                            description = it.description
                        )
                    }
                    emit(Resource.Success(data))
                }
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}