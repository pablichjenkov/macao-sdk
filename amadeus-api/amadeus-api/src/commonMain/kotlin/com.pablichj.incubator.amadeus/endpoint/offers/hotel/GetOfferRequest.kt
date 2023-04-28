package com.pablichj.incubator.amadeus.endpoint.offers.hotel

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class GetOfferRequest(
    val accessToken: AccessToken,
    val offerId: String
)
