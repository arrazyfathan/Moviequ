package com.arrazyfathan.moviequ.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.arrazyfathan.moviequ.data.mapper.toDomain
import com.arrazyfathan.moviequ.data.remote.api.OmdbApi
import com.arrazyfathan.moviequ.domain.Movie
import java.io.IOException

class SearchPagingSource(
    private val movieApi: OmdbApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response = movieApi.searchMovies(page = currentPage, searchQuery = query)
            val movies = response.search

            LoadResult.Page(
                data = movies.map { it.toDomain() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.isEmpty()) null else currentPage + 1,
            )
        } catch (e: IOException) {
            LoadResult.Error(
                Exception(
                    "Network error: Unable to load movies. Please check your connection and try again."
                )
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                Exception("Server error: Unable to load movies. Please try again later.")
            )
        } catch (e: Exception) {
            LoadResult.Error(Exception("Unexpected error: ${e.localizedMessage}"))
        }
    }
}
