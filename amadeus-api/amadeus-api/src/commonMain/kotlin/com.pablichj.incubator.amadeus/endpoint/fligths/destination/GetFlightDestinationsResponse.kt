package com.pablichj.incubator.amadeus.endpoint.fligths.destination

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

sealed class GetFlightDestinationsResponse() {
    class Error(val error: AmadeusError) : GetFlightDestinationsResponse()
    class Success(val flightDestinationsBody: String) : GetFlightDestinationsResponse()
}
