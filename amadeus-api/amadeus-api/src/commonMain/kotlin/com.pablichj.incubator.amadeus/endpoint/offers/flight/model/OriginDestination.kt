package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class OriginDestination (
    val id: String,
    val originLocationCode: String,
    val destinationLocationCode: String,
    val departureDateTimeRange: DepartureDateTimeRange
)
