package com.elsa.giantbomb.repository

import com.elsa.giantbomb.BuildConfig
import com.elsa.giantbomb.entities.DataResult
import com.elsa.giantbomb.entities.Response
import com.elsa.giantbomb.repository.apis.GameAPI
import com.elsa.giantbomb.utility.Constants

/**
 * Repository class
 */
class GameRepository(private val api: GameAPI) {
    suspend fun getGameResult(keyword: String): DataResult<List<Response.GameItem>> {
        return try {
            val gameResult = api.getGameResult(
                apiKey = BuildConfig.API_KEY,
                filter = "name:$keyword",
                format = Constants.JSON
            )
            DataResult.Success(gameResult.results)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}