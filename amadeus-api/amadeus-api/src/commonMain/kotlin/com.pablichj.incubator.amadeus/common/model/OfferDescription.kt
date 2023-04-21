package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferDescription (
    val text: String,
    val lang: String
)