package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class AssociatedRecord (
    val reference: String,
    val originSystemCode: String
)