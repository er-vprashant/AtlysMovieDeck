package com.prashant.atlysmoviedeck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prashant.atlysmoviedeck.ui.screens.detail.MovieDetailScreen
import com.prashant.atlysmoviedeck.ui.screens.list.MovieListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MovieList,
        modifier = modifier
    ) {
        composable(Routes.MovieList) {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetail(movieId))
                }
            )
        }

        composable(
            route = Routes.movieDetailPattern,
            arguments = listOf(
                navArgument(Routes.MovieIdArg) { type = NavType.LongType }
            )
        ) {
            MovieDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
