package com.pablichj.incubator.amadeus.endpoint.accesstoken

import AmadeusError
import com.pablichj.incubator.amadeus.common.Envs
import com.pablichj.incubator.amadeus.common.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAccessTokenUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<GetAccessTokenRequest, GetAccessTokenResponse> {

    override suspend fun doWork(params: GetAccessTokenRequest): GetAccessTokenResponse {
        val result = withContext(dispatcher.Unconfined) {
            runCatching {
                val response = httpClient.submitForm(
                    url = tokenUrl,
                    formParameters = Parameters.build {
                        append(
                            "grant_type",
                            params.accessTokenGrantType
                        ) // TODO: Create a separate Param(key,value) and do a for each
                        append("client_id", params.clientId)
                        append("client_secret", params.clientSecret)
                    }
                )
                if (response.status.isSuccess()) {
                    GetAccessTokenResponse.Success(response.body())
                } else {
                    GetAccessTokenResponse.Error(AmadeusError.fromErrorJsonString(response.bodyAsText()))
                }
            }
        }
        return result.getOrElse {
            return GetAccessTokenResponse.Error(AmadeusError.fromException(it))
        }
    }

    companion object {
        private val tokenUrl = "${Envs.TEST.hostUrl}/v1/security/oauth2/token"
        const val AccessTokenGrantType = "client_credentials"
    }

}