package com.arrazyfathan.moviequ.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arrazyfathan.moviequ.data.local.dao.MovieDao
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract val dao: MovieDao
}
