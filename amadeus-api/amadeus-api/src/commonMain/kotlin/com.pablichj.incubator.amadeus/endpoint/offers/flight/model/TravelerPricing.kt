package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class TravelerPricing (
    val travelerId: String,
    val fareOption: String,
    val travelerType: String,
    val price: TravelerPricingPrice,
    val fareDetailsBySegment: List<FareDetailsBySegment>
)