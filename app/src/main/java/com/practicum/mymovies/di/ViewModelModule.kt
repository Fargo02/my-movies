package com.practicum.mymovies.di

import com.practicum.mymovies.presentation.movies.MoviesSearchViewModel
import com.practicum.mymovies.presentation.cast.MoviesCastViewModel
import com.practicum.mymovies.presentation.history.HistoryViewModel
import com.practicum.mymovies.presentation.names.NamesSearchViewModel
import com.practicum.mymovies.presentation.poster.PosterViewModel
import com.practicum.mymovies.presentation.poster.about.AboutViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesSearchViewModel(get(), androidApplication())
    }
    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId, get())
    }

    viewModel {
        NamesSearchViewModel(get(), androidApplication())
    }

    viewModel {
        HistoryViewModel(androidContext(), get())
    }
}