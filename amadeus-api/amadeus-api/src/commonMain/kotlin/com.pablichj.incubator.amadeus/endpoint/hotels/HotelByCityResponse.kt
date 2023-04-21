package com.pablichj.incubator.amadeus.endpoint.hotels

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelsByCityBody

sealed class HotelByCityResponse {
    class Error(val error: AmadeusError) : HotelByCityResponse()
    class Success(val hotelsByCityBody: HotelsByCityBody) : HotelByCityResponse()
}
