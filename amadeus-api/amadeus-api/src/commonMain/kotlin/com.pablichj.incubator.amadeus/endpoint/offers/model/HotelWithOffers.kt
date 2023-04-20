package com.pablichj.incubator.amadeus.endpoint.offers.model

import com.pablichj.incubator.amadeus.common.model.Offer
import kotlinx.serialization.Serializable

@Serializable
data class HotelWithOffers(
    val type: String,
    val hotel: HotelSearchInfo,
    val available: Boolean,
    val offers: List<Offer>,
    val self: String
)