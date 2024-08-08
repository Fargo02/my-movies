package com.practicum.mymovies.ui.poster.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.mymovies.domain.api.MoviesInteractor
import com.practicum.mymovies.domain.models.MovieDetails
import com.practicum.mymovies.ui.poster.models.AboutState

class AboutViewModel(private val movieId: String,
                     private val moviesInteractor: MoviesInteractor, ) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        moviesInteractor.searchMoviesDetails(movieId, object : MoviesInteractor.MoviesDetailsConsumer {

            override fun consume(foundMoviesDetails: MovieDetails?, errorMessage: String?) {
                if (foundMoviesDetails != null) {
                    stateLiveData.postValue(AboutState.Content(foundMoviesDetails))
                } else {
                    stateLiveData.postValue(AboutState.Error(errorMessage ?: "Unknown error"))
                }
            }
        })
    }
}