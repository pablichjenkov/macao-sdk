package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightOffersConfirmationRequestBody(
    val type: String,
    val flightOffers: List<FlightOffer>
)
