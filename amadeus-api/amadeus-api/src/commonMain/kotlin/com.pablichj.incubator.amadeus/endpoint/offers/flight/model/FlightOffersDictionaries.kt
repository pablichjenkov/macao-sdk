package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightOffersDictionaries (
    val locations: Map<String, Location>,
    val aircraft: Map<String, String>,
    val currencies: Map<String, String>,
    val carriers: Map<String, String>
)