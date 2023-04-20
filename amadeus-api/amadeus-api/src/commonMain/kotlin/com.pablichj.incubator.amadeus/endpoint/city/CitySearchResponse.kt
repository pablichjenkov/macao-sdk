package com.pablichj.incubator.amadeus.endpoint.city

import AmadeusError

sealed class CitySearchResponse() {
    class Error(val error: AmadeusError) : CitySearchResponse()
    class Success(val citySearchBody: String) : CitySearchResponse()
}
