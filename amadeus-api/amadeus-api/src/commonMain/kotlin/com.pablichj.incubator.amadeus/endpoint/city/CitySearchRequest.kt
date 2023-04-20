package com.pablichj.incubator.amadeus.endpoint.city

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class CitySearchRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)