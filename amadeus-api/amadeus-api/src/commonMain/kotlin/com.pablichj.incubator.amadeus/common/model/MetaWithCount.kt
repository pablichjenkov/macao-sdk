package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class MetaWithCount (
    val count: Long,
    val links: Links
)
