package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class Contact (
    val phone: String,
    val email: String
)