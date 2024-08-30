package com.arrazyfathan.moviequ.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arrazyfathan.moviequ.data.local.dao.MovieDao
import com.arrazyfathan.moviequ.data.local.dao.RemoteKeyDao
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import com.arrazyfathan.moviequ.data.local.entity.RemoteKeys

@Database(
    entities = [MovieEntity::class, RemoteKeys::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MovieDao
    abstract fun getRemoteKeysDao(): RemoteKeyDao
}
