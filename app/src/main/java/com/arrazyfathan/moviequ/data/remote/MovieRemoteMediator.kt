package com.arrazyfathan.moviequ.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.arrazyfathan.moviequ.data.local.database.MovieDatabase
import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import com.arrazyfathan.moviequ.data.mapper.toMovieEntity
import com.arrazyfathan.moviequ.data.remote.api.OmdbApi
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(private val movieDb: MovieDatabase, private val movieApi: OmdbApi) :
    RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND -> {
                    state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    val nextKey = state.pages.lastOrNull { it.data.isNotEmpty() }?.nextKey ?: 1
                    nextKey + 1
                }
            }

            val movies = movieApi.searchMovies(page = loadKey)

            movieDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDb.dao.clearAll()
                }
                val movieEntity = movies.search.map { it.toMovieEntity() }
                movieDb.dao.upsertAll(movieEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = movies.search.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
