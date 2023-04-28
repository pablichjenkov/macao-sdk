package com.pablichj.incubator.amadeus.endpoint.offers.flight

import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffersRequestBody

class FlightOffersRequest(
    val accessToken: AccessToken,
    val body: FlightOffersRequestBody
)