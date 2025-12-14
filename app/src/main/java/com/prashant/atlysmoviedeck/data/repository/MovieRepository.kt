package com.prashant.atlysmoviedeck.data.repository

import com.prashant.atlysmoviedeck.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeTrendingMovies(searchQuery: String): Flow<List<Movie>>
    fun observeMovie(movieId: Long): Flow<Movie?>

    suspend fun refreshTrendingMovies(): Result<Unit>
}
