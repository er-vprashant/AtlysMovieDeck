package com.prashant.atlysmoviedeck.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prashant.atlysmoviedeck.data.local.AppDatabase
import com.prashant.atlysmoviedeck.data.local.MovieEntity
import com.prashant.atlysmoviedeck.data.local.MovieRemoteKeyEntity
import com.prashant.atlysmoviedeck.data.remote.TmdbApi
import com.prashant.atlysmoviedeck.data.toEntity

@OptIn(ExperimentalPagingApi::class)
class TrendingRemoteMediator(
    private val api: TmdbApi,
    private val database: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val remoteKey = database.movieRemoteKeyDao().remoteKeyByMovieId(lastItem.id)
                    remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = api.getTrendingMovies(page = page)
            val now = System.currentTimeMillis()
            val startIndex = (page - 1) * state.config.pageSize
            val movies = response.results.mapIndexed { index, dto ->
                dto.toEntity(nowEpochMs = now, sortIndex = (startIndex + index).toLong())
            }

            val endOfPaginationReached = page >= response.totalPages || movies.isEmpty()
            val nextKey = if (endOfPaginationReached) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieRemoteKeyDao().clearRemoteKeys()
                    database.movieDao().clearAll()
                }

                val keys = movies.map { MovieRemoteKeyEntity(movieId = it.id, nextKey = nextKey) }
                database.movieRemoteKeyDao().insertAll(keys)
                database.movieDao().upsertAll(movies)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}
