package com.practicum.mymovies.presentation.names

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.mymovies.R
import com.practicum.mymovies.domain.api.SearchNameInteractor
import com.practicum.mymovies.domain.models.Person
import com.practicum.mymovies.util.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NamesSearchViewModel(
    private val searchNameInteractor: SearchNameInteractor,
    private val application: Application
) : AndroidViewModel(application) {

    private val stateLiveData = MutableLiveData<NamesState>()
    fun observeState(): LiveData<NamesState> = mediatorStateLiveData

    private val mediatorStateLiveData = MediatorLiveData<NamesState>().also { liveData ->
        liveData.addSource(stateLiveData) { namesState ->
            liveData.value = when (namesState) {
                is NamesState.Content -> NamesState.Content(namesState.people)
                is NamesState.Empty -> namesState
                is NamesState.Error -> namesState
                is NamesState.Loading -> namesState
            }
        }
    }

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var latestSearchText: String? = null

    private var searchJob: Job? = null

    //избавился от Handler, заменил на coroutines
    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        latestSearchText = changedText

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }


    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(NamesState.Loading)

            viewModelScope.launch {
                searchNameInteractor
                    .searchNames(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundNames: List<Person>?, errorMessage: String?) {
        val persons = mutableListOf<Person>()
        if (foundNames != null) {
            persons.addAll(foundNames)
        }

        when {
            errorMessage != null -> {
                renderState(NamesState.Error(message = application.getString(
                    R.string.something_went_wrong)))
                showToast.postValue(errorMessage!!)
            }
            persons.isEmpty() -> {
                renderState(NamesState.Empty(message = application.getString(R.string.nothing_found)))
            }
            else -> {
                renderState(NamesState.Content(people = persons))
            }
        }
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}