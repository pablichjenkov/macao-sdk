package com.pablichj.incubator.amadeus.endpoint.city

import AmadeusError
import Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitySearchUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<CitySearchRequest, CitySearchResponse> {

    override suspend fun doWork(params: CitySearchRequest): CitySearchResponse {
        val resp = withContext(dispatcher.Unconfined) {
            httpClient.get(hotelsByCityUrl) {
                url {
                    params.queryParams.forEach {
                        parameters.append(it.key, it.value)
                    }
                }
                header(HttpHeaders.Authorization, params.accessToken.authorization)
            }
        }

        return if (resp.status.isSuccess()) {
            CitySearchResponse.Success(resp.body())
        } else {
            CitySearchResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v1/reference-data/locations/cities"
    }
}