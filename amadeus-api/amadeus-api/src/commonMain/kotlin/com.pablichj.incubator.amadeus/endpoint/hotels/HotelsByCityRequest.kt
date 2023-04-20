package com.pablichj.incubator.amadeus.endpoint.hotels

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class HotelsByCityRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)
