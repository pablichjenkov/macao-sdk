package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightOfferPrice (
    val currency: String,
    val total: String,
    val base: String,
    val fees: List<FlightOfferFee>,
    val grandTotal: String
)