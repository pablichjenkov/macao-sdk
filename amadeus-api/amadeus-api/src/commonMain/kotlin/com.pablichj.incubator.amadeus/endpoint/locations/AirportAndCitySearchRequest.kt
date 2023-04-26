package com.pablichj.incubator.amadeus.endpoint.locations

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class AirportAndCitySearchRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)