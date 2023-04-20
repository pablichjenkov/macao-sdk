package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Variations (
    val average: Average,
    val changes: List<Change>
)