package com.practicum.mymovies.di

import com.practicum.mymovies.data.HistoryRepositoryImpl
import com.practicum.mymovies.data.MoviesRepositoryImpl
import com.practicum.mymovies.data.SearchHistoryRepositoryImpl
import com.practicum.mymovies.data.SearchNameRepositoryImpl
import com.practicum.mymovies.data.converters.MovieDbConverter
import com.practicum.mymovies.data.converters.MoviesCastConverter
import com.practicum.mymovies.domain.api.MoviesRepository
import com.practicum.mymovies.domain.api.SearchHistoryRepository
import com.practicum.mymovies.domain.api.SearchNameRepository
import com.practicum.mymovies.domain.db.HistoryRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { MoviesCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            networkClient = get(),
            localStorage =  get(),
            movieCastConverter = get(),
            appDatabase = get(),
            movieDbConvertor= get(),
        )
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl()
    }

    single<SearchNameRepository> {
        SearchNameRepositoryImpl(get())
    }

    factory { MovieDbConverter() }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }
}