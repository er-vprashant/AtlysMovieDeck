package com.prashant.atlysmoviedeck.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.prashant.atlysmoviedeck.data.local.AppDatabase
import com.prashant.atlysmoviedeck.data.local.MovieDao
import com.prashant.atlysmoviedeck.data.remote.TmdbApi
import com.prashant.atlysmoviedeck.data.toDomain
import com.prashant.atlysmoviedeck.data.toEntity
import com.prashant.atlysmoviedeck.domain.Movie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstMovieRepository @Inject constructor(
    private val api: TmdbApi,
    private val database: AppDatabase,
    private val movieDao: MovieDao
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun observeTrendingMoviesPaged(searchQuery: String): Flow<PagingData<Movie>> {
        return if (searchQuery.isBlank()) {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20,
                    prefetchDistance = 1,
                    enablePlaceholders = false
                ),
                remoteMediator = TrendingRemoteMediator(api = api, database = database),
                pagingSourceFactory = { movieDao.pagingSourceMovies() }
            ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
        } else {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20,
                    prefetchDistance = 1,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { movieDao.pagingSourceMoviesByTitle(searchQuery) }
            ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
        }
    }

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
            val response = api.getTrendingMovies(page = 1)
            val now = System.currentTimeMillis()

            val entities = response.results
                .mapIndexed { index, dto ->
                    dto.toEntity(nowEpochMs = now, sortIndex = index.toLong())
                }

            movieDao.clearAll()
            movieDao.upsertAll(entities)
        }
    }
}
