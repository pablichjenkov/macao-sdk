package com.pablichj.incubator.amadeus.endpoint.offers.flight

import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchResponseBody
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Datum
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Dictionaries
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.Meta
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class FlightOffersResponse (
    val meta: Meta,
    val data: List<Datum>,
    val dictionaries: Dictionaries
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): CitySearchResponseBody = Json.decodeFromString(json)
    }
}