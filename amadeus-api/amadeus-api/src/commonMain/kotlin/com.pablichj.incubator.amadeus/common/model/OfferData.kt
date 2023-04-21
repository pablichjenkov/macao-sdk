package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferData (
    val type: String,
    val hotel: HotelInGetOffer,
    val available: Boolean,
    val offers: List<OfferInGetOffer>
)
