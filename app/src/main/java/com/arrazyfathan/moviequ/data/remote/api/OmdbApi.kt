package com.arrazyfathan.moviequ.data.remote.api

import com.arrazyfathan.moviequ.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String = "790c82e",
        @Query("s") searchQuery: String = "friends",
        @Query("page") page: Int,
    ): MovieResponseDto


    companion object {
        const val BASE_URL = "http://www.omdbapi.com/"
    }
}
