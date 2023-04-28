package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightFilters (
    val cabinRestrictions: List<CabinRestriction>
)