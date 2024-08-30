package com.arrazyfathan.moviequ.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("Response")
    val response: String = "",
    @SerializedName("Search")
    val search: List<MovieDto> = listOf(),
    @SerializedName("totalResults")
    val totalResults: String = ""
)