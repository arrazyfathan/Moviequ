package com.arrazyfathan.moviequ.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String,
)
