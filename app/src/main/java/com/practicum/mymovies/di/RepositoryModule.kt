package com.practicum.mymovies.di

import com.practicum.mymovies.data.MoviesRepositoryImpl
import com.practicum.mymovies.data.SearchHistoryRepositoryImpl
import com.practicum.mymovies.data.SearchNameRepositoryImpl
import com.practicum.mymovies.data.converters.MoviesCastConverter
import com.practicum.mymovies.domain.api.MoviesRepository
import com.practicum.mymovies.domain.api.SearchHistoryRepository
import com.practicum.mymovies.domain.api.SearchNameRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { MoviesCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            networkClient = get(),
            localStorage =  get(),
            movieCastConverter = get()
        )
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl()
    }

    single<SearchNameRepository> {
        SearchNameRepositoryImpl(get())
    }
}