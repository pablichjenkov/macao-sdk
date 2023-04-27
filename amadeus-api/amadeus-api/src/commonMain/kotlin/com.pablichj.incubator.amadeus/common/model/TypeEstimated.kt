package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class TypeEstimated (
    val category: String = "",
    val beds: Long? = -1,
    val bedType: String? = ""
)
