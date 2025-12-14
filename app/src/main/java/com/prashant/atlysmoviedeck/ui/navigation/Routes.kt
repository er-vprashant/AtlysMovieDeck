package com.prashant.atlysmoviedeck.ui.navigation

object Routes {
    const val MovieList = "movie_list"
    const val MovieDetail = "movie_detail"

    const val MovieIdArg = "movieId"

    fun movieDetail(movieId: Long): String = "$MovieDetail/$movieId"
    const val movieDetailPattern: String = "$MovieDetail/{$MovieIdArg}"
}
