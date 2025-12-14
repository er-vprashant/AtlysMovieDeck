package com.prashant.atlysmoviedeck.data.repository

import com.prashant.atlysmoviedeck.data.local.MovieDao
import com.prashant.atlysmoviedeck.data.remote.TmdbApi
import com.prashant.atlysmoviedeck.data.toDomain
import com.prashant.atlysmoviedeck.data.toEntity
import com.prashant.atlysmoviedeck.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstMovieRepository(
    private val api: TmdbApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun observeTrendingMovies(searchQuery: String): Flow<List<Movie>> {
        val source = if (searchQuery.isBlank()) {
            movieDao.observeMovies()
        } else {
            movieDao.observeMoviesByTitle(searchQuery)
        }

        return source.map { entities -> entities.map { it.toDomain() } }
    }

    override fun observeMovie(movieId: Long): Flow<Movie?> {
        return movieDao.observeMovieById(movieId).map { it?.toDomain() }
    }

    override suspend fun refreshTrendingMovies(): Result<Unit> {
        return runCatching {
            val response = api.getTrendingMovies()
            val now = System.currentTimeMillis()

            val entities = response.results
                .take(20)
                .map { it.toEntity(now) }

            movieDao.clearAll()
            movieDao.upsertAll(entities)
        }
    }
}
