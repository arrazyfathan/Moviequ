package com.arrazyfathan.moviequ.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String,
)
