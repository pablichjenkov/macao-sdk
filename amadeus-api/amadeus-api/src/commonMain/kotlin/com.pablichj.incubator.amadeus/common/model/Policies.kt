package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Policies (
    val paymentType: String,
    val cancellation: Cancellation
)