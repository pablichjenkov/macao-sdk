package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class VariationsInGetOffer (
    val average: Average = Average("0"),
    val changes: List<ChangeInOfferInGetOffer>
)
