package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceInOffer (
    val currency: String,
    val base: String,
    val total: String,
    val variations: VariationsInGetOffer
)
