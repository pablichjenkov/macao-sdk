package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class CabinRestriction (
    val cabin: String,
    val coverage: String,
    val originDestinationIds: List<String>
)