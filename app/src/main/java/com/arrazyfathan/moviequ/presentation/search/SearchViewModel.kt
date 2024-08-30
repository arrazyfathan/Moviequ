@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.arrazyfathan.moviequ.presentation.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.arrazyfathan.moviequ.domain.Movie
import com.arrazyfathan.moviequ.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _query = MutableStateFlow("")
    val movies: Flow<PagingData<Movie>> =
        _query
            .debounce(300)
            .filter { it.length > 3 }
            .flatMapLatest { query -> movieRepository.searchMovies(query) }

    fun onQueryChange(query: String) {
        _query.value = query
    }
}
