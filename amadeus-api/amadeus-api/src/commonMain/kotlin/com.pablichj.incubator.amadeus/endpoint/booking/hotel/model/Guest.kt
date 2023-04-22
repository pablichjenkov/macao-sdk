package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class Guest (
    val name: Name,
    val contact: Contact
)
