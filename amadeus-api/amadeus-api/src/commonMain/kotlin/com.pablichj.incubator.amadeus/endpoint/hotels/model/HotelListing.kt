package com.pablichj.incubator.amadeus.endpoint.hotels.model

import com.pablichj.incubator.amadeus.common.model.AddressCountry
import com.pablichj.incubator.amadeus.common.model.Distance
import com.pablichj.incubator.amadeus.common.model.GeoCode
import kotlinx.serialization.Serializable

@Serializable
data class HotelListing(
    val chainCode: String,
    val iataCode: String,
    val dupeId: Long,
    val name: String,
    val hotelId: String,
    val geoCode: GeoCode,
    val address: AddressCountry? = null,
    val distance: Distance,
    val lastUpdate: String = ""
)
