package com.pablichj.incubator.amadeus.endpoint.booking.hotel

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchBody

sealed class HotelBookingResponse {
    class Error(val error: AmadeusError) : HotelBookingResponse()
    class Success(val hotelBookingBody: String) : HotelBookingResponse()
}
