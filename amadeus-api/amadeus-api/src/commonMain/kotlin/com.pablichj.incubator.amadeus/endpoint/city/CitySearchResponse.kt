package com.pablichj.incubator.amadeus.endpoint.city

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchBody

sealed class CitySearchResponse {
    class Error(val error: AmadeusError) : CitySearchResponse()
    class Success(val citySearchBody: CitySearchBody) : CitySearchResponse()
}
