package com.pablichj.incubator.amadeus.endpoint.airport.model

@kotlinx.serialization.Serializable
data class AirportLocationSelf (
    val href: String,
    val methods: List<String> // Only Http Methods Allowed
)