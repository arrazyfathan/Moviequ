package com.arrazyfathan.moviequ.domain

import androidx.paging.PagingData
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun loadHomeMovie(): Flow<PagingData<MovieEntity>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}
