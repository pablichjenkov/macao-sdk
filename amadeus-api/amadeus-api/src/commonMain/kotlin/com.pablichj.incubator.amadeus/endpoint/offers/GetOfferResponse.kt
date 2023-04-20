package com.pablichj.incubator.amadeus.endpoint.offers

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.model.HotelOffersBody

sealed class GetOfferResponse() {
    class Error(val error: AmadeusError) : GetOfferResponse()
    class Success(val offer: String) : GetOfferResponse()
}
