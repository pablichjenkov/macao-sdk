package com.pablichj.incubator.amadeus.endpoint.offers.model

import kotlinx.serialization.Serializable

@Serializable
data class HotelSearchInfo (
    val type: String,
    val hotelId: String,
    val chainCode: String,
    val dupeId: String,
    val name: String,
    val cityCode: String,
    val latitude: Double,
    val longitude: Double
)
