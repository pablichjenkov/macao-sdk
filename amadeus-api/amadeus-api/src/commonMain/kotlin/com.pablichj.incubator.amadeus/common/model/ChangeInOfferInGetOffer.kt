package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangeInOfferInGetOffer (
    val startDate: String,
    val endDate: String,
    val base: String
)