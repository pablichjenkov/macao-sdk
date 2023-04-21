package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class OfferPolicies (
    val paymentType: String,
    val cancellation: OfferCancellation
)