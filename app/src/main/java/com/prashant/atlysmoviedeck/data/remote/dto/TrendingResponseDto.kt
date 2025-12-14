package com.prashant.atlysmoviedeck.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingResponseDto(
    @SerialName("results") val results: List<MovieDto> = emptyList()
)
