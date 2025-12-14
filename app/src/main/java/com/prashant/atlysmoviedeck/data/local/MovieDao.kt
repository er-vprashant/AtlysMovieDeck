package com.prashant.atlysmoviedeck.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY fetchedAtEpochMs DESC, title ASC")
    fun observeMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies ORDER BY sortIndex ASC")
    fun pagingSourceMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY title ASC")
    fun observeMoviesByTitle(query: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY sortIndex ASC")
    fun pagingSourceMoviesByTitle(query: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    fun observeMovieById(movieId: Long): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
