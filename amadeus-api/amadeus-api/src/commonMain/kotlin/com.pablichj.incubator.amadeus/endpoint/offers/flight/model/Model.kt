package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Datum (
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
    val price: DatumPrice,
    val pricingOptions: PricingOptions,
    val validatingAirlineCodes: List<String>,
    val travelerPricings: List<TravelerPricing>
)
@kotlinx.serialization.Serializable
data class Itinerary (
    val duration: String,
    val segments: List<Segment>
)
@kotlinx.serialization.Serializable
data class Segment (
    val departure: Arrival,
    val arrival: Arrival,
    val carrierCode: String,
    val number: String,
    val aircraft: Aircraft,
    val operating: Operating,
    val duration: String,
    val id: String,
    val numberOfStops: Long,
    val blacklistedInEU: Boolean
)
@kotlinx.serialization.Serializable
data class Aircraft (
    val code: String
)
@kotlinx.serialization.Serializable
data class Arrival (
    val iataCode: String,
    val terminal: String = "",
    val at: String
)
@kotlinx.serialization.Serializable
data class Operating (
    val carrierCode: String
)
@kotlinx.serialization.Serializable
data class DatumPrice (
    val currency: String,
    val total: String,
    val base: String,
    val fees: List<Fee>,
    val grandTotal: String
)
@kotlinx.serialization.Serializable
data class Fee (
    val amount: String,
    val type: String
)
@kotlinx.serialization.Serializable
data class PricingOptions (
    val fareType: List<String>,
    val includedCheckedBagsOnly: Boolean
)
@kotlinx.serialization.Serializable
data class TravelerPricing (
    val travelerId: String,
    val fareOption: String,
    val travelerType: String,
    val price: TravelerPricingPrice,
    val fareDetailsBySegment: List<FareDetailsBySegment>
)
@kotlinx.serialization.Serializable
data class FareDetailsBySegment (
    val segmentId: String,
    val cabin: String,
    val fareBasis: String,
    @SerialName("class")
    val fareDetailsBySegmentClass: String,
    val brandedFare: String = "",
    val includedCheckedBags: IncludedCheckedBags
)
@kotlinx.serialization.Serializable
data class IncludedCheckedBags (
    val quantity: Long
)
@kotlinx.serialization.Serializable
data class TravelerPricingPrice (
    val currency: String,
    val total: String,
    val base: String
)
@kotlinx.serialization.Serializable
data class Dictionaries (
    val locations: Map<String, Location>,
    val aircraft: Map<String, String>,
    val currencies: Currencies,
    val carriers: Carriers
)
@kotlinx.serialization.Serializable
data class Carriers (
    @SerialName("6X")
    val the6X: String = ""
)
@kotlinx.serialization.Serializable
data class Currencies (
    @SerialName("USD")
    val usd: String = ""
)
@kotlinx.serialization.Serializable
data class Location (
    val cityCode: String = "",
    val countryCode: String = ""
)

@kotlinx.serialization.Serializable
data class Meta (
    val count: Long
)
