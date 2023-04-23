package com.pablichj.incubator.amadeus.endpoint.offers

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

class GetOfferUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<GetOfferRequest, GetOfferResponse> {

    override suspend fun doWork(params: GetOfferRequest): GetOfferResponse {
        val result = withContext(dispatcher.Unconfined) {
            runCatching {
                val response = httpClient.get(getOfferUrl) {
                    url {
                        appendEncodedPathSegments("/hotel-offers/${params.offerId}")
                    }
                    header(HttpHeaders.Authorization, params.accessToken.authorization)
                }
                if (response.status.isSuccess()) {
                    GetOfferResponse.Success(response.body())
                } else {
                    GetOfferResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            return GetOfferResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val getOfferUrl = "${Envs.TEST.hostUrl}/v3/shopping"
    }

}