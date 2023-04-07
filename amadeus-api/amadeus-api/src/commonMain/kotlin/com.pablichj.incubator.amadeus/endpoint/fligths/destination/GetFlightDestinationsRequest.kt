package com.pablichj.incubator.amadeus.endpoint.fligths.destination

import QueryParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

data class GetFlightDestinationsRequest(
    val accessToken: AccessToken,
    val queryParams: List<QueryParam>
)
