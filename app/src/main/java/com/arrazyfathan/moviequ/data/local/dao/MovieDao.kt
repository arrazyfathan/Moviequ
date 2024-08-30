package com.arrazyfathan.moviequ.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun clearAll()
}
