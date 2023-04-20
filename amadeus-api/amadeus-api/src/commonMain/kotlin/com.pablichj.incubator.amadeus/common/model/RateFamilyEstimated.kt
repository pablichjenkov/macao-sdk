package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class RateFamilyEstimated (
    val code: String,
    val type: String
)