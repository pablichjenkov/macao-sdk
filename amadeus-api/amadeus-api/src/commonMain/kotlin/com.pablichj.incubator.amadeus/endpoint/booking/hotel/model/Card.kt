package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class Card (
    val vendorCode: String,
    val cardNumber: String,
    val expiryDate: String
)