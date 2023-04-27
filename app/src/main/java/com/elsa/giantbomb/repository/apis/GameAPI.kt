package com.elsa.giantbomb.repository.apis

import com.elsa.giantbomb.entities.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Network API
 */
interface GameAPI {
    @GET("api/games")
    suspend fun getGameResult(
        @Query("api_key") apiKey: String,
        @Query("filter") filter: String,
        @Query("format") format: String,
    ): Response.GameResult
}