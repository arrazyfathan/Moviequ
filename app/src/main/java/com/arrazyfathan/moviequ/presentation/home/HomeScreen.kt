package com.arrazyfathan.moviequ.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.arrazyfathan.moviequ.ui.components.MovieItem
import com.arrazyfathan.moviequ.ui.components.MovieItemShimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, onSearchBarClicked: () -> Unit = {}) {
    val movies = homeViewModel.moviePagingFlow.collectAsLazyPagingItems()
    val loadState = movies.loadState.mediator

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberLazyListState()

    val elevation by remember {
        derivedStateOf {
            if (
                scrollState.firstVisibleItemIndex > 0 ||
                    scrollState.firstVisibleItemScrollOffset > 0
            ) {
                4.dp
            } else {
                0.dp
            }
        }
    }

    val isScrollingDown = remember {
        derivedStateOf {
            scrollState.isScrollInProgress && scrollState.firstVisibleItemScrollOffset > 0
        }
    }
    val showFab = remember {
        derivedStateOf { isScrollingDown.value || scrollState.firstVisibleItemIndex > 0 }
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(shadowElevation = elevation) {
                MediumTopAppBar(
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            titleContentColor = Color.Black,
                            scrolledContainerColor = Color.White,
                        ),
                    title = {
                        Text(
                            "Moviequ",
                            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium),
                        )
                    },
                    actions = {
                        IconButton(onClick = onSearchBarClicked) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = Color.Black,
                                contentDescription = null,
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        floatingActionButton = {
            if (showFab.value) {
                AnimatedVisibility(visible = showFab.value, enter = fadeIn(), exit = fadeOut()) {
                    FloatingActionButton(
                        onClick = { coroutineScope.launch { scrollState.animateScrollToItem(0) } },
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        shape = CircleShape,
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Back to Top",
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.White).padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scrollState,
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
                        modifier = modifier.padding(horizontal = 16.dp),
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
                            onClick = {
                                if (isPaginatingError) {
                                    movies.retry()
                                } else {
                                    movies.refresh()
                                }
                            },
                            content = { Text(text = "Retry") },
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White,
                                ),
                        )
                    }
                }
            }
        }
    }
}
