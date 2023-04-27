package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferInMultiHotels (
    val id: String,
    val checkInDate: String,
    val checkOutDate: String,
    val rateCode: String,
    val rateFamilyEstimated: RateFamilyEstimated? = null,
    val room: Room,
    val guests: Guests,
    val price: PriceInMultiHotel,
    val policies: OfferPoliciesInHotels,
    val self: String
)