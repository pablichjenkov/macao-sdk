package com.pablichj.incubator.amadeus.endpoint.offers.hotel.model

import com.pablichj.incubator.amadeus.common.model.HotelInMultiHotelSearch
import com.pablichj.incubator.amadeus.common.model.OfferInMultiHotels
import kotlinx.serialization.Serializable

@Serializable
data class HotelWithOffers(
    val type: String,
    val hotel: HotelInMultiHotelSearch,
    val available: Boolean,
    val offers: List<OfferInMultiHotels>,
    val self: String
)