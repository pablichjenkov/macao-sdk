package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class Traveler (
    val id: String,
    val travelerType: String
)