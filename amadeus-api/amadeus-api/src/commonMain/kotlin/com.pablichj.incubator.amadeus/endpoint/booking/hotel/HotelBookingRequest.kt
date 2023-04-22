package com.pablichj.incubator.amadeus.endpoint.booking.hotel

import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingRequestBody

class HotelBookingRequest(
    val accessToken: AccessToken,
    val body: HotelBookingRequestBody
)