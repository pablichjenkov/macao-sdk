package com.pablichj.incubator.amadeus.endpoint.hotels

import AmadeusError
import Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotelsByCityUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<HotelsByCityRequest, HotelByCityResponse> {

    override suspend fun doWork(params: HotelsByCityRequest): HotelByCityResponse {
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
            HotelByCityResponse.Success(resp.body())
        } else {
            HotelByCityResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v1/reference-data/locations/hotels/by-city"
    }
}
