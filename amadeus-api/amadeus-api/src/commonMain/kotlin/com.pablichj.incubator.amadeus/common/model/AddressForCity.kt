package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressForCity (
    val countryCode: String,
    val stateCode: String
)