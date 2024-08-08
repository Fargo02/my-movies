package com.practicum.mymovies.di

import com.practicum.mymovies.presentation.movies.MoviesSearchViewModel
import com.practicum.mymovies.ui.poster.PosterViewModel
import com.practicum.mymovies.ui.poster.about.AboutViewModel
import org.koin.android.ext.koin.androidApplication
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
}