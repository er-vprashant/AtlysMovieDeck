package com.prashant.atlysmoviedeck.data.repository

import androidx.paging.PagingData
import com.prashant.atlysmoviedeck.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeTrendingMoviesPaged(searchQuery: String): Flow<PagingData<Movie>>
    fun observeTrendingMovies(searchQuery: String): Flow<List<Movie>>
    fun observeMovie(movieId: Long): Flow<Movie?>

    suspend fun refreshTrendingMovies(): Result<Unit>
}
