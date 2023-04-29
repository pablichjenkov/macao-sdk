package com.pablichj.incubator.amadeus.endpoint.booking.hotel.model

import com.pablichj.incubator.amadeus.endpoint.city.model.CitySearchResponseBody
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class HotelBookingResponseBody (
    val data: List<HotelBookingConfirmation>
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): HotelBookingResponseBody = Json.decodeFromString(json)
    }
}



