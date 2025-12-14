package com.prashant.atlysmoviedeck.domain

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterUrl: String,
    val backdropUrl: String
)
