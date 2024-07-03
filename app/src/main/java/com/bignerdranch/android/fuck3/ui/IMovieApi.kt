package com.bignerdranch.android.fuck3.ui

import com.bignerdranch.android.fuck3.ui.models.Movie
import retrofit2.Call
import retrofit2.http.GET

interface IMovieApi {
    @GET("movies")
    fun getMovie(): Call<List<Movie>>
}