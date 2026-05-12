package com.arrazyfathan.moviequ.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.arrazyfathan.moviequ.ui.components.MovieItem
import com.arrazyfathan.moviequ.ui.components.MovieItemShimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    val movies = searchViewModel.movies.collectAsLazyPagingItems()
    val loadState = movies.loadState
    val colorScheme = MaterialTheme.colorScheme

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Scaffold(
        containerColor = colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier =
                Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .imePadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                    searchViewModel.onQueryChange(newValue)
                },
                placeholder = { Text(text = "Search Movie") },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceVariant,
                        unfocusedContainerColor = colorScheme.surfaceVariant,
                        disabledContainerColor = colorScheme.surfaceVariant,
                        focusedIndicatorColor = colorScheme.primary.copy(alpha = 0.4f),
                        unfocusedIndicatorColor = colorScheme.outline.copy(alpha = 0.2f),
                        disabledIndicatorColor = colorScheme.outline.copy(alpha = 0.12f),
                        cursorColor = colorScheme.primary,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                    ),
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .height(60.dp)
                        .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            modifier =
                                Modifier.size(24.dp).clickable {
                                    if (query.isNotEmpty()) {
                                        query = ""
                                        searchViewModel.onQueryChange("")
                                    }
                                },
                            imageVector = Icons.Rounded.Close,
                            tint = colorScheme.onSurfaceVariant,
                            contentDescription = null,
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (query.length > 3) {
                LazyColumn(
                    modifier =
                        Modifier.fillMaxSize()
                            .background(colorScheme.background)
                            .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(movies.itemCount) { index ->
                        val movie = movies[index]
                        movie?.let { MovieItem(movie = it, modifier = Modifier.fillMaxWidth()) }
                    }

                    item {
                        val movieNotFound =
                            movies.itemSnapshotList.items.isEmpty() &&
                                loadState.refresh is LoadState.NotLoading

                        if (movieNotFound) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Movie not found.",
                                    modifier = Modifier.align(Alignment.Center),
                                )
                            }
                        }
                        if (loadState.refresh == LoadState.Loading) {
                            Column { repeat(10) { MovieItemShimmer() } }
                        }

                        if (loadState.append == LoadState.Loading) {
                            MovieItemShimmer()
                        }

                        if (
                            loadState.refresh is LoadState.Error ||
                                loadState.append is LoadState.Error
                        ) {
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
                                    colors = ButtonDefaults.buttonColors(),
                                )
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "Search for movies", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
