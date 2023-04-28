package com.pablichj.incubator.amadeus.endpoint.offers.hotel

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.GetOfferResponseBody

sealed class GetOfferResponse {
    class Error(val error: AmadeusError) : GetOfferResponse()
    class Success(val responseBody: GetOfferResponseBody) : GetOfferResponse()
}
