package com.pablichj.incubator.amadeus.endpoint.city

import AmadeusError
import com.pablichj.incubator.amadeus.common.Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitySearchUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<CitySearchRequest, CitySearchResponse> {

    override suspend fun doWork(params: CitySearchRequest): CitySearchResponse {
        val result = withContext(dispatcher.Unconfined) {
            runCatching {
                val response = httpClient.get(hotelsByCityUrl) {
                    url {
                        params.queryParams.forEach {
                            parameters.append(it.key, it.value)
                        }
                    }
                    header(HttpHeaders.Authorization, params.accessToken.authorization)
                }
                if (response.status.isSuccess()) {
                    CitySearchResponse.Success(response.body())
                } else {
                    CitySearchResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            return CitySearchResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v1/reference-data/locations/cities"
    }
}