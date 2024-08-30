package com.arrazyfathan.moviequ.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.arrazyfathan.moviequ.domain.Movie
import com.arrazyfathan.moviequ.ui.components.MovieItem

@Composable
fun HomeScreen(movies: LazyPagingItems<Movie>, modifier: Modifier) {
    val context = LocalContext.current
    LaunchedEffect(movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                    context,
                    "Error : " + (movies.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG,
                )
                .show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieItem(movie = movie, modifier = Modifier.fillMaxWidth())
                    }
                }

                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
