package com.prashant.atlysmoviedeck.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeyDao {

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeyByMovieId(movieId: Long): MovieRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<MovieRemoteKeyEntity>)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clearRemoteKeys()
}
