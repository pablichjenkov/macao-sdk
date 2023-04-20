package com.pablichj.incubator.amadeus.endpoint.hotels.model

import com.pablichj.incubator.amadeus.common.model.Address
import com.pablichj.incubator.amadeus.common.model.Distance
import com.pablichj.incubator.amadeus.common.model.GeoCode
import kotlinx.serialization.Serializable

@Serializable
data class HotelListingInfo(
    val chainCode: String,
    val iataCode: String,
    val dupeId: Long,
    val name: String,
    val hotelId: String,
    val geoCode: GeoCode,
    val address: Address? = null,
    val distance: Distance,
    val lastUpdate: String = ""
)
