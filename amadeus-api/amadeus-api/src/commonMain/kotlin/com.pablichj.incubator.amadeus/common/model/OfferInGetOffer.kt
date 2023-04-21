package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferInGetOffer (
    val id: String,
    val checkInDate: String,
    val checkOutDate: String,
    val rateCode: String,
    val rateFamilyEstimated: RateFamilyEstimated,
    val description: OfferDescription,
    val room: Room,
    val guests: Guests,
    val price: PriceInOffer,
    val policies: OfferPolicies
)
