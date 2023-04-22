package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class Payment (
    val method: String,
    val card: Card
)