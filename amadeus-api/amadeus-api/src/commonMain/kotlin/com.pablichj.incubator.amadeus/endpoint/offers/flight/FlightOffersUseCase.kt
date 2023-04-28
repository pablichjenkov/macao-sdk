package com.pablichj.incubator.amadeus.endpoint.offers.flight

import AmadeusError
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.common.Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FlightOffersUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<FlightOffersRequest, CallResult<FlightOffersResponse>> {

    override suspend fun doWork(params: FlightOffersRequest): CallResult<FlightOffersResponse> {
        val result = withContext(dispatcher.Unconfined) {
            runCatching {
                val response = httpClient.post(flightOffersUrl) {
                    contentType(ContentType.Application.Json)
                    header(HttpHeaders.Authorization, params.accessToken.authorization)
                    header(HttpHeaders.XHttpMethodOverride, "GET")
                    setBody(params.body)
                }
                if (response.status.isSuccess()) {
                    CallResult.Success<FlightOffersResponse>(response.body())
                } else {
                    CallResult.Error<FlightOffersResponse>(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            it.printStackTrace()
            return CallResult.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val flightOffersUrl = "${Envs.TEST.hostUrl}/v2/shopping/flight-offers"
    }

}