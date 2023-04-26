package com.pablichj.incubator.amadeus.endpoint.offers

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.offers.model.GetOfferBody

sealed class GetOfferResponse {
    class Error(val error: AmadeusError) : GetOfferResponse()
    class Success(val responseBody: GetOfferBody) : GetOfferResponse()
}
