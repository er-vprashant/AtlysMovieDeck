package com.prashant.atlysmoviedeck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prashant.atlysmoviedeck.ui.screens.detail.MovieDetailPlaceholderScreen
import com.prashant.atlysmoviedeck.ui.screens.list.MovieListPlaceholderScreen

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
            MovieListPlaceholderScreen(
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
            val movieId = it.arguments?.getLong(Routes.MovieIdArg) ?: return@composable
            MovieDetailPlaceholderScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
