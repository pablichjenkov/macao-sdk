package com.pablichj.incubator.amadeus.endpoint.hotellist

import AmadeusError
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

sealed class ListHotelByCityResponse() {
    class Error(val error: AmadeusError) : ListHotelByCityResponse()
    class Success(val hotelList: String) : ListHotelByCityResponse()
}
