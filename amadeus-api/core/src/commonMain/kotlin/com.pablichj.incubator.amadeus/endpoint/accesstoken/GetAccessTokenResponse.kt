package com.pablichj.incubator.amadeus.endpoint.accesstoken

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

sealed class GetAccessTokenResponse() {
    class Error(val error: AmadeusError) : GetAccessTokenResponse()
    class Success(val accessToken: AccessToken) : GetAccessTokenResponse()
}
