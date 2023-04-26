package com.pablichj.incubator.amadeus.endpoint.locations

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchResponseBody

sealed class AirportAndCitySearchResponse {
    class Error(val error: AmadeusError) : AirportAndCitySearchResponse()
    class Success(val responseBody: String) : AirportAndCitySearchResponse()
}
