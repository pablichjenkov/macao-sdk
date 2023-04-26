package com.pablichj.incubator.amadeus.endpoint.booking.hotel

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingResponseBody

sealed class HotelBookingResponse {
    class Error(val error: AmadeusError) : HotelBookingResponse()
    class Success(val responseBody: HotelBookingResponseBody) : HotelBookingResponse()
}
