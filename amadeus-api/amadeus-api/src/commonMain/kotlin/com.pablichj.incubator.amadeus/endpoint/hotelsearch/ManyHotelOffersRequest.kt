package com.pablichj.incubator.amadeus.endpoint.hotelsearch

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class ManyHotelOffersRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)
