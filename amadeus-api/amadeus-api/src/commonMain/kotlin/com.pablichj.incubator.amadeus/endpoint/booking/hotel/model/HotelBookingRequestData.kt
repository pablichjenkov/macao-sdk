package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

@kotlinx.serialization.Serializable
data class HotelBookingRequestData(
    val offerId: String,
    val guests: List<Guest>,
    val payments: List<Payment>
)
