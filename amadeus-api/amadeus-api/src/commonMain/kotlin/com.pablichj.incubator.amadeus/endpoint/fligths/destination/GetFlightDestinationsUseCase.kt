package com.pablichj.incubator.amadeus.endpoint.fligths.destination

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

class GetFlightDestinationsUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<GetFlightDestinationsRequest, GetFlightDestinationsResponse> {

    override suspend fun doWork(params: GetFlightDestinationsRequest): GetFlightDestinationsResponse {
        val resp = withContext(dispatcher.Unconfined) {
            httpClient.get(flightDestinationsUrl) {
                url {
                    params.queryParams.forEach {
                        parameters.append(it.key, it.value)
                    }
                }
                header(HttpHeaders.Authorization, params.accessToken.authorization)
            }
        }

        return if (resp.status.isSuccess()) {
            GetFlightDestinationsResponse.Success(resp.bodyAsText())
        } else {
            GetFlightDestinationsResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val flightDestinationsUrl = "${Envs.TEST.hostUrl}/v1/shopping/flight-destinations"
    }

}
