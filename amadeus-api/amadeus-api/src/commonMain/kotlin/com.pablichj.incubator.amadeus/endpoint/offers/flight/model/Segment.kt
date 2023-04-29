package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class Segment (
    val departure: Departure,
    val arrival: Arrival,
    val carrierCode: String,
    val number: String,
    val aircraft: Aircraft,
    val operating: Operating,
    val duration: String,
    val id: String,
    val numberOfStops: Long,
    val blacklistedInEU: Boolean
)
