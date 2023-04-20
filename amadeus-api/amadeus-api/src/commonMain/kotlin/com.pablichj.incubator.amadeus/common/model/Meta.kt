package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Meta (
    val count: Long,
    val links: Links
)
