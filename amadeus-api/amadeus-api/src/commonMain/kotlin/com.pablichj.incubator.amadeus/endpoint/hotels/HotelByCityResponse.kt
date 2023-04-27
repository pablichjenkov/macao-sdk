package com.pablichj.incubator.amadeus.endpoint.hotels

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelsByCityResponseBody

sealed class HotelByCityResponse {
    class Error(val error: AmadeusError) : HotelByCityResponse()
    class Success(val responseBody: HotelsByCityResponseBody) : HotelByCityResponse()
}
