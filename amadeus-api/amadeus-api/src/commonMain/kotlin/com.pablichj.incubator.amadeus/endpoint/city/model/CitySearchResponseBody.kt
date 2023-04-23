package com.pablichj.incubator.amadeus.endpoint.city.model

import com.pablichj.incubator.amadeus.common.model.City
import com.pablichj.incubator.amadeus.common.model.MetaWithCount
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class CitySearchResponseBody(
    val meta: MetaWithCount,
    val data: List<City>
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): CitySearchResponseBody = Json.decodeFromString(json)
    }
}