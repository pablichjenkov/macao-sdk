package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class DepartureDateTimeRange (
    val date: String,
    val time: String
)