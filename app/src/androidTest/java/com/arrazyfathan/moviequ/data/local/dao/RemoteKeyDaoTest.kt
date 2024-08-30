package com.arrazyfathan.moviequ.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.arrazyfathan.moviequ.data.local.database.MovieDatabase
import com.arrazyfathan.moviequ.data.local.entity.RemoteKeys
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RemoteKeyDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var remoteKeyDao: RemoteKeyDao

    @Before
    fun setupDatabase() {
        database =
            Room.inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    MovieDatabase::class.java,
                )
                .allowMainThreadQueries()
                .build()

        remoteKeyDao = database.getRemoteKeysDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAll_insertsRemoteKeysCorrectly() = runTest {
        val remoteKeys = listOf(
            RemoteKeys(id = "tt0111161", prevKey = null, currentPage = 1, nextKey = 2),
            RemoteKeys(id = "tt0068646", prevKey = 1, currentPage = 2, nextKey = 3)
        )

        remoteKeyDao.insertAll(remoteKeys)

        val retrievedKey1 = remoteKeyDao.getRemoteKeyByMovieID("tt0111161")
        val retrievedKey2 = remoteKeyDao.getRemoteKeyByMovieID("tt0068646")

        assertNotNull(retrievedKey1)
        assertEquals(1, retrievedKey1?.currentPage)

        assertNotNull(retrievedKey2)
        assertEquals(2, retrievedKey2?.currentPage)
    }

    @Test
    fun getRemoteKeyByMovieID_returnsNullWhenNotExists() = runTest {
        val remoteKey = remoteKeyDao.getRemoteKeyByMovieID("nonexistent_id")
        assertNull(remoteKey)
    }

    @Test
    fun clearRemoteKeys_deletesAllRemoteKeys() = runTest {
        val remoteKeys = listOf(
            RemoteKeys(id = "tt0111161", prevKey = null, currentPage = 1, nextKey = 2),
            RemoteKeys(id = "tt0068646", prevKey = 1, currentPage = 2, nextKey = 3)
        )

        remoteKeyDao.insertAll(remoteKeys)
        remoteKeyDao.clearRemoteKeys()

        val retrievedKey1 = remoteKeyDao.getRemoteKeyByMovieID("tt0111161")
        val retrievedKey2 = remoteKeyDao.getRemoteKeyByMovieID("tt0068646")

        assertNull(retrievedKey1)
        assertNull(retrievedKey2)
    }

    @Test
    fun getCreationTime_returnsMostRecentTimestamp() = runTest {
        val remoteKeys = listOf(
            RemoteKeys(id = "tt0111161", prevKey = null, currentPage = 1, nextKey = 2, createdAt = 1000L),
            RemoteKeys(id = "tt0068646", prevKey = 1, currentPage = 2, nextKey = 3, createdAt = 2000L)
        )

        remoteKeyDao.insertAll(remoteKeys)

        val mostRecentTimestamp = remoteKeyDao.getCreationTime()

        assertNotNull(mostRecentTimestamp)
        assertEquals(2000L, mostRecentTimestamp)
    }
}
