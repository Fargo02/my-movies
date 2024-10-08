package com.practicum.mymovies.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.mymovies.data.NetworkClient
import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchRequest
import com.practicum.mymovies.data.dto.Response
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsRequest
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastRequest
import com.practicum.mymovies.data.dto.nameSearch.NameSearchRequest

class RetrofitNetworkClient(
    private val imdbService: IMDbApiService,
    private val context: Context,
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return when (dto) {
            is MoviesSearchRequest -> {
                val response = imdbService.searchMovies(dto.expression).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            }
            is MoviesDetailsRequest -> {
                val response = imdbService.getMovieDetails(dto.movieId).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            }
            is MoviesFullCastRequest -> {
                val response = imdbService.getFullCast(dto.movieId).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            }
            is NameSearchRequest -> {
                val response = imdbService.searchName(dto.expression).execute()
                val body = response.body()
                return if (body != null) {
                    body.apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            }
            else -> {
                Response().apply { resultCode = 400 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}