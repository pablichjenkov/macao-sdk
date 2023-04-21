package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferCancellation (
    val description: CancellationDescription,
    val type: String
)