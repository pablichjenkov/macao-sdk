package com.pablichj.incubator.amadeus.endpoint.offers.hotel

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.MultiHotelOffersResponseBody

sealed class MultiHotelOffersResponse() {
    class Error(val error: AmadeusError) : MultiHotelOffersResponse()
    class Success(val responseBody: MultiHotelOffersResponseBody) : MultiHotelOffersResponse()
}
