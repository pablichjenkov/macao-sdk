package com.pablichj.incubator.amadeus.endpoint.hotels.model

import com.pablichj.incubator.amadeus.common.model.Meta
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class HotelListingBody(
    val data: List<HotelListingInfo>,
    val meta: Meta
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): HotelListingBody = Json.decodeFromString(json)
    }
}