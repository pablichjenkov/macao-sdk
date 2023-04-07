package com.pablichj.incubator.amadeus.endpoint.hotellist

import AmadeusError
import Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListHotelsByCityUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<ListHotelsByCityRequest, ListHotelByCityResponse> {

    override suspend fun doWork(params: ListHotelsByCityRequest): ListHotelByCityResponse {
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
            ListHotelByCityResponse.Success(resp.bodyAsText())
        } else {
            ListHotelByCityResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v1/reference-data/locations/hotels/by-city"
    }

}