package com.pablichj.incubator.amadeus

import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

internal interface AmadeusService {

    /***
     * Token
     */
    //@FormUrlEncoded
    //@POST("v1/security/oauth2/token")
//    fun getAccessToken(
//        @Field("client_id") clientId: String,
//        @Field("client_secret") clientSecret: String,
//        @Field("grant_type") grant_type: String = "client_credentials"
//    ): Call<AccessToken>

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        grant_type: String = "client_credentials"
    ): AccessToken

}