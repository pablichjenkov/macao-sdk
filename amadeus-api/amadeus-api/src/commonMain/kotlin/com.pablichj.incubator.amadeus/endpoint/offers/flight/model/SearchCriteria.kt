package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class SearchCriteria (
    val maxFlightOffers: Long,
    val flightFilters: FlightFilters
)