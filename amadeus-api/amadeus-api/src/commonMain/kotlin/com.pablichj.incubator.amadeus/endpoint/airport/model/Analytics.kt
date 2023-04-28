package com.pablichj.incubator.amadeus.endpoint.airport.model

@kotlinx.serialization.Serializable
data class Analytics (
    val travelers: TravelersAnalytics
)