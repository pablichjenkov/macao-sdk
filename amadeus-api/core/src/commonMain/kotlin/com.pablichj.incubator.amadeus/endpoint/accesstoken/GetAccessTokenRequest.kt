package com.pablichj.incubator.amadeus.endpoint.accesstoken

data class GetAccessTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val accessTokenGrantType: String
)
