package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class TravelerPricingPrice (
    val currency: String,
    val total: String,
    val base: String
)