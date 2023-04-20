package com.pablichj.incubator.amadeus.endpoint.offers.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class HotelOffersBody(
    val data: List<HotelWithOffers>
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(
            json: String
        ): HotelOffersBody = Json.decodeFromString(json)
    }
}