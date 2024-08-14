package com.practicum.mymovies.data.network

import com.practicum.mymovies.data.dto.moviesSearch.MoviesSearchResponse
import com.practicum.mymovies.data.dto.movieDetails.MoviesDetailsResponse
import com.practicum.mymovies.data.dto.moviesCast.MoviesFullCastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MoviesDetailsResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MoviesFullCastResponse>
}