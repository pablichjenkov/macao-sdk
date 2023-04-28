package com.pablichj.incubator.amadeus.endpoint.airport.model

import com.pablichj.incubator.amadeus.common.model.GeoCode

@kotlinx.serialization.Serializable
data class AirportInfo (
    val type: String,
    val subType: String,
    val name: String,
    val detailedName: String,
    val id: String,
    val self: AirportLocationSelf,
    val timeZoneOffset: String,
    val iataCode: String,
    val geoCode: GeoCode,
    val address: AddressForAirport,
    val analytics: Analytics? = null
)