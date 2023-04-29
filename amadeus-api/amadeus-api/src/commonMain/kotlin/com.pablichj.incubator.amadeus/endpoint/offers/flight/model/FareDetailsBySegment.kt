package com.pablichj.incubator.amadeus.endpoint.offers.flight.model

import kotlinx.serialization.SerialName

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