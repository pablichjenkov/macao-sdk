package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Offer (
    val id: String,
    val checkInDate: String,
    val checkOutDate: String,
    val rateCode: String,
    val rateFamilyEstimated: RateFamilyEstimated,
    val room: Room,
    val guests: Guests,
    val price: Price,
    val policies: Policies,
    val self: String
)