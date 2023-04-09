package com.pablichj.incubator.amadeus.endpoint.hotelsearch

import AmadeusError

sealed class ManyHotelOffersResponse() {
    class Error(val error: AmadeusError) : ManyHotelOffersResponse()
    class Success(val manyHotelOffers: String) : ManyHotelOffersResponse()
}
