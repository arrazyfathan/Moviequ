package com.arrazyfathan.moviequ.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.arrazyfathan.moviequ.data.local.entity.RemoteKeys

@Dao
interface RemoteKeyDao {

    @Upsert suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key WHERE imdb_id = :id")
    suspend fun getRemoteKeyByMovieID(id: String): RemoteKeys?

    @Query("DELETE FROM remote_key") suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM remote_key ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
