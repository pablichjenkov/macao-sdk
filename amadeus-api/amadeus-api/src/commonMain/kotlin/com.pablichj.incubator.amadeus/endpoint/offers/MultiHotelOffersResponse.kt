package com.pablichj.incubator.amadeus.endpoint.offers

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.model.MultiHotelOffersBody

sealed class MultiHotelOffersResponse() {
    class Error(val error: AmadeusError) : MultiHotelOffersResponse()
    class Success(val multiHotelOffers: MultiHotelOffersBody) : MultiHotelOffersResponse()
}
