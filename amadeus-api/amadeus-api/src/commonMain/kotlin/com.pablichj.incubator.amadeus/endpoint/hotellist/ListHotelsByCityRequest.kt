package com.pablichj.incubator.amadeus.endpoint.hotellist

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class ListHotelsByCityRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)
