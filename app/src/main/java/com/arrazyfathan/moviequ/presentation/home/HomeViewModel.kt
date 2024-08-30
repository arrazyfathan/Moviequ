package com.arrazyfathan.moviequ.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.arrazyfathan.moviequ.data.mapper.toMovie
import com.arrazyfathan.moviequ.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val moviePagingFlow =
        movieRepository
            .loadHomeMovie()
            .map { pagingData -> pagingData.map { it.toMovie() } }
            .cachedIn(viewModelScope)
}
