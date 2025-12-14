package com.prashant.atlysmoviedeck.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingResponseDto(
    @SerialName("page") val page: Int = 1,
    @SerialName("total_pages") val totalPages: Int = 1,
    @SerialName("results") val results: List<MovieDto> = emptyList()
)
