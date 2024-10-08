package com.practicum.mymovies.di

import com.practicum.mymovies.domain.api.MoviesInteractor
import com.practicum.mymovies.domain.api.SearchHistoryInteractor
import com.practicum.mymovies.domain.api.SearchNameInteractor
import com.practicum.mymovies.domain.impl.MoviesInteractorImpl
import com.practicum.mymovies.domain.impl.SearchHistoryInteractorImpl
import com.practicum.mymovies.domain.impl.SearchNameInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(
            repository = get()
        )
    }

    single<SearchHistoryInteractor> {
        SearchHistoryInteractorImpl()
    }

    single<SearchNameInteractor> {
        SearchNameInteractorImpl(get())
    }
}