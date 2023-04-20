package com.pablichj.incubator.amadeus.endpoint.offers

import AmadeusError
import Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MultiHotelOffersUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<MultiHotelOffersRequest, MultiHotelOffersResponse> {

    override suspend fun doWork(params: MultiHotelOffersRequest): MultiHotelOffersResponse {
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
            MultiHotelOffersResponse.Success(resp.body())
        } else {
            MultiHotelOffersResponse.Error(resp.body<AmadeusError>()) // todo Do propers error parsing
        }

    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v3/shopping/hotel-offers"
    }

}