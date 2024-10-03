package com.practicum.mymovies.data.network

import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchResponse
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsResponse
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastResponse
import com.practicum.mymovies.data.dto.nameSearch.NameSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MoviesDetailsResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MoviesFullCastResponse>

    @GET("/en/API/SearchName/k_zcuw1ytf/{expression}")
    suspend fun searchName(@Path("expression") expression: String): NameSearchResponse
}