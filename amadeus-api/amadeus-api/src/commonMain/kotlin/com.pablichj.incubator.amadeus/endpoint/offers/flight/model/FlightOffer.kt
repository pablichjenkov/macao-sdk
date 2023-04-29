package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

@kotlinx.serialization.Serializable
data class FlightOffer (
    val type: String,
    val id: String,
    val source: String,
    val instantTicketingRequired: Boolean,
    val nonHomogeneous: Boolean,
    val oneWay: Boolean,
    val lastTicketingDate: String,
    val lastTicketingDateTime: String,
    val numberOfBookableSeats: Long,
    val itineraries: List<Itinerary>,
    val price: FlightOfferPrice,
    val pricingOptions: PricingOptions,
    val validatingAirlineCodes: List<String>,
    val travelerPricings: List<TravelerPricing>
)










