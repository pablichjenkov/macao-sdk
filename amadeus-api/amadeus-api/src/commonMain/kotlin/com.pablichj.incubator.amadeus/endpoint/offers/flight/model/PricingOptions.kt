package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class PricingOptions (
    val fareType: List<String>,
    val includedCheckedBagsOnly: Boolean
)