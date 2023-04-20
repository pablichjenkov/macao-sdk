package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Room (
    val type: String,
    val typeEstimated: TypeEstimated,
    val description: Description
)