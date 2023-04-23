package com.pablichj.incubator.amadeus.endpoint.booking.hotel

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

class HotelBookingUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<HotelBookingRequest, HotelBookingResponse> {

    override suspend fun doWork(params: HotelBookingRequest): HotelBookingResponse {
        val result = withContext(dispatcher.Unconfined) {
            runCatching {
                val response = httpClient.post(hotelBookingUrl) {
                    contentType(ContentType.Application.Json)
                    header(HttpHeaders.Authorization, params.accessToken.authorization)
                    setBody(params.body)
                }
                if (response.status.isSuccess()) {
                    HotelBookingResponse.Success(response.body())
                } else {
                    HotelBookingResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            return HotelBookingResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val hotelBookingUrl = "${Envs.TEST.hostUrl}/booking/hotel-bookings"
    }
}