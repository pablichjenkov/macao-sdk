package com.pablichj.incubator.amadeus.endpoint.airport.model

@kotlinx.serialization.Serializable
data class AddressForAirport (
    val cityName: String,
    val cityCode: String,
    val countryName: String,
    val countryCode: String,
    val stateCode: String,
    val regionCode: String
)