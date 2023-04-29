package com.pablichj.incubator.amadeus.endpoint.airport.model

import com.pablichj.incubator.amadeus.common.model.MetaWithCount
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelsByCityResponseBody
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
class AirportSearchResponseBody(
    val meta: MetaWithCount,
    val data: List<AirportInfo>
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): AirportSearchResponseBody = Json.decodeFromString(json)
    }
}

