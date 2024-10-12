package com.erkan.simplemovieapp.data.network

import com.erkan.simplemovieapp.BuildConfig
import com.erkan.simplemovieapp.data.models.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("discover/movie")
    suspend fun fetchMovies(
        @Query("api_key") apiKey: String = BuildConfig.api_key,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MoviesDto
}