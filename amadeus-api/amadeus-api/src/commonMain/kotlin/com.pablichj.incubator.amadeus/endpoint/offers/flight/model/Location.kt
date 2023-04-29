package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class Location (
    val cityCode: String = "",
    val countryCode: String = ""
)