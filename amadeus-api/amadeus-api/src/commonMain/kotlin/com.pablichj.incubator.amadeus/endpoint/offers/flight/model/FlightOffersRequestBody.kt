package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.OriginDestination
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.SearchCriteria
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Traveler

@kotlinx.serialization.Serializable
data class FlightOffersRequestBody(
    val currencyCode: String,
    val originDestinations: List<OriginDestination>,
    val travelers: List<Traveler>,
    val sources: List<String>,
    val searchCriteria: SearchCriteria
)