package com.prashant.atlysmoviedeck.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKeyEntity(
    @PrimaryKey val movieId: Long,
    val nextKey: Int?
)
