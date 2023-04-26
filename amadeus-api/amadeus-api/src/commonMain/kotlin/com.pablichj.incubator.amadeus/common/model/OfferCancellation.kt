package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferCancellation (
    val description: CancellationDescription? = null,
    val type: String? = null,
    val amount: String? = null,
    val deadline: String? = null
)