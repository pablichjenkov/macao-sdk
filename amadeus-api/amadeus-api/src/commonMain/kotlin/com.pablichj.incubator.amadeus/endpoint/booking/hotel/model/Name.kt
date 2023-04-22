package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class Name (
    val title: String,
    val firstName: String,
    val lastName: String
)