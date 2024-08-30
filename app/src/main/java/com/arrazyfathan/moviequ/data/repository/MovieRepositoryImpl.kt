package com.arrazyfathan.moviequ.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arrazyfathan.moviequ.data.local.database.MovieDatabase
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import com.arrazyfathan.moviequ.data.paging.MovieRemoteMediator
import com.arrazyfathan.moviequ.data.paging.SearchPagingSource
import com.arrazyfathan.moviequ.data.remote.api.OmdbApi
import com.arrazyfathan.moviequ.domain.Movie
import com.arrazyfathan.moviequ.domain.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
@Singleton
class MovieRepositoryImpl
@Inject
constructor(private val movieApi: OmdbApi, private val movieDatabase: MovieDatabase) :
    MovieRepository {

    override fun loadHomeMovie(): Flow<PagingData<MovieEntity>> {
        return Pager(
                config = PagingConfig(pageSize = 10, prefetchDistance = 10, initialLoadSize = 10),
                remoteMediator = MovieRemoteMediator(movieDb = movieDatabase, movieApi = movieApi),
                pagingSourceFactory = { movieDatabase.getMoviesDao().pagingSource() },
            )
            .flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { SearchPagingSource(movieApi, query) },
            )
            .flow
    }
}
