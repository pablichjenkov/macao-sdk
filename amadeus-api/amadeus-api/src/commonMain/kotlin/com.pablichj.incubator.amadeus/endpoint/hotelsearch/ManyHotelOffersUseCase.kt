package com.pablichj.incubator.amadeus.endpoint.hotelsearch

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

class ManyHotelOffersUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<ManyHotelOffersRequest, ManyHotelOffersResponse> {

    override suspend fun doWork(params: ManyHotelOffersRequest): ManyHotelOffersResponse {
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
            ManyHotelOffersResponse.Success(resp.bodyAsText())
        } else {
            ManyHotelOffersResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v3/shopping/hotel-offers"
    }

}