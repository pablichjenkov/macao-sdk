package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class City (
    val type: String,
    val subType: String,
    val name: String,
    val iataCode: String = "",
    val address: AddressForCity,
    val geoCode: GeoCode
)
