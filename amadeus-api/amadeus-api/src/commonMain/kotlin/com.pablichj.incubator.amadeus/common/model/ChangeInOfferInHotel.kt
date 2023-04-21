package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangeInOfferInHotel (
    val startDate: String,
    val endDate: String,
    val total: String
)
