package com.pablichj.incubator.amadeus.endpoint.offers.flight

import com.pablichj.incubator.amadeus.common.model.MetaWithCount
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffer
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffersDictionaries
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class FlightOffersConfirmationResponse(
    val meta: MetaWithCount,
    val data: List<FlightOffer>,
    val dictionaries: FlightOffersDictionaries
) {
    fun toJson(): String = Json.encodeToString(this)

    companion object {
        fun fromJson(json: String): FlightOffersConfirmationResponse = Json.decodeFromString(json)
    }
}
