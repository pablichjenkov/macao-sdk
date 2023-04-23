package com.pablichj.incubator.amadeus.endpoint.hotels

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

class HotelsByCityUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<HotelsByCityRequest, HotelByCityResponse> {

    override suspend fun doWork(params: HotelsByCityRequest): HotelByCityResponse {
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
                    HotelByCityResponse.Success(response.body())
                } else {
                    HotelByCityResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            return HotelByCityResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val hotelsByCityUrl =
            "${Envs.TEST.hostUrl}/v1/reference-data/locations/hotels/by-city"
    }
}
