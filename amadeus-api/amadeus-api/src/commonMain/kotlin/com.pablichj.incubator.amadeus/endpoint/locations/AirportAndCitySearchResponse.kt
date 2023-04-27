package com.pablichj.incubator.amadeus.endpoint.locations

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchResponseBody
import com.pablichj.incubator.amadeus.endpoint.locations.model.AirportSearchResponseBody

sealed class AirportAndCitySearchResponse {
    class Error(val error: AmadeusError) : AirportAndCitySearchResponse()
    class Success(val responseBody: AirportSearchResponseBody) : AirportAndCitySearchResponse()
}
