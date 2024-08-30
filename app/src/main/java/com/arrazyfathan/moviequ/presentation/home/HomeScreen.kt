package com.arrazyfathan.moviequ.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.arrazyfathan.moviequ.ui.components.MovieItem
import com.arrazyfathan.moviequ.ui.components.MovieItemShimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, onSearchBarClicked: () -> Unit = {}) {
    val movies = homeViewModel.moviePagingFlow.collectAsLazyPagingItems()
    val loadState = movies.loadState.mediator

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        scrolledContainerColor = Color.White,
                    ),
                title = { Text("Moviequ", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                actions = {
                    IconButton(onClick = onSearchBarClicked) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = Color.Black,
                            contentDescription = "Localized description",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier.fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                movie?.let { MovieItem(movie = it, modifier = Modifier.fillMaxWidth()) }
            }

            item {
                if (loadState?.refresh == LoadState.Loading) {
                    Column { repeat(10) { MovieItemShimmer() } }
                }

                if (loadState?.append == LoadState.Loading) {
                    MovieItemShimmer()
                }

                if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                    val isPaginatingError =
                        (loadState.append is LoadState.Error) || movies.itemCount > 1
                    val error =
                        if (loadState.append is LoadState.Error)
                            (loadState.append as LoadState.Error).error
                        else (loadState.refresh as LoadState.Error).error

                    val modifier =
                        if (isPaginatingError) {
                            Modifier.padding(8.dp)
                        } else {
                            Modifier.fillParentMaxSize()
                        }
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier.size(64.dp),
                                imageVector = Icons.Rounded.Warning,
                                contentDescription = null,
                            )
                        }

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = error.message ?: error.toString(),
                            textAlign = TextAlign.Center,
                        )

                        Button(
                            onClick = { movies.refresh() },
                            content = { Text(text = "Refresh") },
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White,
                                ),
                        )
                    }
                }
            }
        }
    }
}
