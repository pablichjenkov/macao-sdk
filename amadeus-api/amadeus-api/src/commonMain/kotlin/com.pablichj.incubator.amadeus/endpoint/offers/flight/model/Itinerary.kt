package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class Itinerary (
    val duration: String,
    val segments: List<Segment>
)
