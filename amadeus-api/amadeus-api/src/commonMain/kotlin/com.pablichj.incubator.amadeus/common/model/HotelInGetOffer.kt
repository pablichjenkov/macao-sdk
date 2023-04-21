package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class HotelInGetOffer (
    val type: String,
    val hotelId: String,
    val chainCode: String,
    val name: String,
    val cityCode: String,
    val address: AddressCountry,
    val amenities: List<String>
)