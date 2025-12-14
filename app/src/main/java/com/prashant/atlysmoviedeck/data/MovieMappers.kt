package com.prashant.atlysmoviedeck.data

import com.prashant.atlysmoviedeck.data.local.MovieEntity
import com.prashant.atlysmoviedeck.data.remote.dto.MovieDto
import com.prashant.atlysmoviedeck.domain.Movie

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toEntity(nowEpochMs: Long): MovieEntity {
    return MovieEntity(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        fetchedAtEpochMs = nowEpochMs
    )
}

fun MovieEntity.toDomain(): Movie {
    val posterUrl = if (posterPath.isNotBlank()) "$TMDB_IMAGE_BASE_URL$posterPath" else ""
    val backdropUrl = if (backdropPath.isNotBlank()) "$TMDB_IMAGE_BASE_URL$backdropPath" else ""

    return Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl
    )
}
