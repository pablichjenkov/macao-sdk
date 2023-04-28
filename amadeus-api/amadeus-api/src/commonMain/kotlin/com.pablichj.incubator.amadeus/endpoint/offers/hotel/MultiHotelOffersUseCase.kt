package com.pablichj.incubator.amadeus.endpoint.offers.hotel

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

class MultiHotelOffersUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<MultiHotelOffersRequest, MultiHotelOffersResponse> {

    override suspend fun doWork(params: MultiHotelOffersRequest): MultiHotelOffersResponse {
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
                    MultiHotelOffersResponse.Success(response.body())
                } else {
                    MultiHotelOffersResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            it.printStackTrace()
            return MultiHotelOffersResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val hotelsByCityUrl = "${Envs.TEST.hostUrl}/v3/shopping/hotel-offers"
    }

}