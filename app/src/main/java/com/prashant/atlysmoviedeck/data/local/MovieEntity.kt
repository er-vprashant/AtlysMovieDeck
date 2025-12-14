package com.prashant.atlysmoviedeck.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String,
    val fetchedAtEpochMs: Long,
    val sortIndex: Long
)
