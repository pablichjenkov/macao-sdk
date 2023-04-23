package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class HotelBookingConfirmation (
    val type: String,
    val id: String,
    val providerConfirmationId: String,
    val associatedRecords: List<AssociatedRecord>
)