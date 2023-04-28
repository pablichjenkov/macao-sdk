package com.pablichj.incubator.amadeus.endpoint.offers.hotel

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class MultiHotelOffersRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)
