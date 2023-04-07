package com.pablichj.incubator.amadeus.endpoint.accesstoken.model

import com.pablichj.incubator.amadeus.AccessTokenTb
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
) {
    fun toTable(createdTimestamp: Long): AccessTokenTb {
        return AccessTokenTb(
            access_token = this.accessToken,
            application_name = this.applicationName,
            client_id = this.clientId,
            expires_in = this.expiresIn.toLong(),
            scope = this.scope,
            state = this.state,
            token_type = this.tokenType,
            type = this.type,
            username = this.username,
            authorization = this.authorization,
            created_timestamp = createdTimestamp
        )
    }
}

internal fun AccessTokenTb.toModel(): AccessToken {
    return AccessToken(
        access_token,
        application_name,
        client_id,
        expires_in?.toInt() ?: 0,
        scope,
        state,
        token_type,
        type,
        username,
        authorization
    )
}

