package com.arrazyfathan.moviequ.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arrazyfathan.moviequ.presentation.home.HomeScreen
import com.arrazyfathan.moviequ.presentation.home.HomeViewModel
import com.arrazyfathan.moviequ.presentation.search.SearchScreen
import com.arrazyfathan.moviequ.presentation.search.SearchViewModel
import com.arrazyfathan.moviequ.ui.theme.MoviequTheme
import com.arrazyfathan.moviequ.utils.Material3Transitions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            }

            MoviequTheme {
                val navController = rememberNavController()
                val density = LocalDensity.current

                NavHost(
                    navController = navController,
                    startDestination = "home",
                    enterTransition = {
                        Material3Transitions.SharedXAxisEnterTransition(density)
                    },
                    popEnterTransition = {
                        Material3Transitions.SharedXAxisPopEnterTransition(density)
                    },
                    exitTransition = {
                        Material3Transitions.SharedXAxisExitTransition(density)
                    },
                    popExitTransition = {
                        Material3Transitions.SharedXAxisPopExitTransition(density)
                    },
                ) {
                    composable(route = "home") {
                        val viewMode = hiltViewModel<HomeViewModel>()
                        HomeScreen(
                            homeViewModel = viewMode,
                            onSearchBarClicked = { navController.navigate("search") },
                        )
                    }
                    composable("search") {
                        val viewModel = hiltViewModel<SearchViewModel>()
                        SearchScreen(searchViewModel = viewModel)
                    }
                }
            }
        }
    }
}
