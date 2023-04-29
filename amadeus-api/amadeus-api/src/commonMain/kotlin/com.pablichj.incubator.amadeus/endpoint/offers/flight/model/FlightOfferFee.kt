package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightOfferFee (
    val amount: String,
    val type: String
)