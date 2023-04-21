package com.pablichj.incubator.amadeus.endpoint.hotels.model

import com.pablichj.incubator.amadeus.common.model.MetaWithCount
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class HotelsByCityBody(
    val data: List<HotelListing>,
    val meta: MetaWithCount
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): HotelsByCityBody = Json.decodeFromString(json)
    }
}
