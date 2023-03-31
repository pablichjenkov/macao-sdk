package com.pablichj.incubator.amadeus.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class AccessToken internal constructor(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("application_name") val applicationName: String = "",
    @SerialName("client_id") val clientId: String = "",
    @SerialName("expires_in") val expiresIn: Int = 0,
    val scope: String = "",
    val state: String = "",
    @SerialName("token_type") val tokenType: String = "",
    val type: String = "",
    val username: String = "",
    val authorization: String = "$tokenType $accessToken"
)

