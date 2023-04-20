package com.pablichj.incubator.amadeus.endpoint.offers

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.model.HotelOffersBody

sealed class MultiHotelOffersResponse() {
    class Error(val error: AmadeusError) : MultiHotelOffersResponse()
    class Success(val manyHotelOffers: HotelOffersBody) : MultiHotelOffersResponse()
}
