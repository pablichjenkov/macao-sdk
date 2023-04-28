package com.pablichj.incubator.amadeus.endpoint.airport

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.airport.model.AirportSearchResponseBody

sealed class AirportAndCitySearchResponse {
    class Error(val error: AmadeusError) : AirportAndCitySearchResponse()
    class Success(val responseBody: AirportSearchResponseBody) : AirportAndCitySearchResponse()
}
