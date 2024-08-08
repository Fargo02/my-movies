package com.practicum.mymovies.ui.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.mymovies.domain.api.MoviesInteractor
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.ui.poster.models.AboutState

class PosterViewModel(private val posterUrl: String) : ViewModel() {

    private val urlLiveData = MutableLiveData(posterUrl)
    fun observeUrl(): LiveData<String> = urlLiveData
}

