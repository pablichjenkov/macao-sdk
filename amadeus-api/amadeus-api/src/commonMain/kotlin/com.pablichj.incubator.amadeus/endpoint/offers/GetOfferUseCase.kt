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

class GetOfferUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<GetOfferRequest, GetOfferResponse> {

    override suspend fun doWork(params: GetOfferRequest): GetOfferResponse {
        val resp = withContext(dispatcher.Unconfined) {
            httpClient.get(getOfferUrl) {
                url {
                    appendEncodedPathSegments("/hotel-offers/${params.offerId}")
                }
                header(HttpHeaders.Authorization, params.accessToken.authorization)
            }
        }

        return if (resp.status.isSuccess()) {
            GetOfferResponse.Success(resp.body())
        } else {
            GetOfferResponse.Error(resp.body<AmadeusError>()) // todo Do propers error parsing
        }

    }

    companion object {
        private val getOfferUrl = "${Envs.TEST.hostUrl}/v3/shopping"
    }

}