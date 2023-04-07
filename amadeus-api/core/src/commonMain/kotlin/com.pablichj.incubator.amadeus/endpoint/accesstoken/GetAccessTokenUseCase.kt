package com.pablichj.incubator.amadeus.endpoint.accesstoken

import AmadeusError
import apiHost
import com.pablichj.incubator.amadeus.SingleUseCase
import com.pablichj.incubator.amadeus.httpClient
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAccessTokenUseCase(
    private val dispatcher: Dispatchers
) : SingleUseCase<GetAccessTokenRequest, GetAccessTokenResponse> {

    override suspend fun doWork(params: GetAccessTokenRequest): GetAccessTokenResponse {
        val resp = withContext(dispatcher.Unconfined) {
            httpClient.submitForm(
                url = tokenUrl,
                formParameters = Parameters.build {
                    append("grant_type", params.accessTokenGrantType)
                    append("client_id", params.clientId)
                    append("client_secret", params.clientSecret)
                }
            )
        }

        return if (resp.status.isSuccess()) {
            GetAccessTokenResponse.Success(resp.body<AccessToken>())
        } else {
            GetAccessTokenResponse.Error(resp.body<AmadeusError>())
        }

    }

    companion object {
        private val tokenUrl = "$apiHost/v1/security/oauth2/token"
        const val AccessTokenGrantType = "client_credentials"
    }

}