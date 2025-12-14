package com.prashant.atlysmoviedeck.data.remote

import com.prashant.atlysmoviedeck.data.remote.dto.TrendingResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TrendingResponseDto
}
