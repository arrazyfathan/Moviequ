package com.arrazyfathan.moviequ.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.arrazyfathan.moviequ.data.local.database.MovieDatabase
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java,
        ).allowMainThreadQueries().build()

        movieDao = database.getMoviesDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun upsertAll_insertsMoviesCorrectly() = runTest {
        val movieList = listOf(
            MovieEntity(imdbID = "fafa", title = "Movie 1", year = "2018", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fufu", title = "Movie 2", year = "2017", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fofo", title = "Movie 3", year = "2016", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fifi", title = "Movie 4", year = "2015", poster = "some", type = "movie"),
        )

        movieDao.upsertAll(movieList)

        val pagingSource = movieDao.pagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        if (pagingSource is PagingSource.LoadResult.Page) {
            assertEquals(4, pagingSource.data.size)
            assertEquals("Movie 1", pagingSource.data[0].title)
        }
    }

    @Test
    fun clearAll_deletesAllMovies() = runTest {
        val movieList = listOf(
            MovieEntity(imdbID = "fafa", title = "Movie 1", year = "2018", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fufu", title = "Movie 2", year = "2017", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fofo", title = "Movie 3", year = "2016", poster = "some", type = "movie"),
            MovieEntity(imdbID = "fifi", title = "Movie 4", year = "2015", poster = "some", type = "movie"),
        )

        movieDao.upsertAll(movieList)
        movieDao.clearAll()

        val pagingSource = movieDao.pagingSource().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        if (pagingSource is PagingSource.LoadResult.Page) {
            assertEquals(0, pagingSource.data.size)
        }
    }

}
