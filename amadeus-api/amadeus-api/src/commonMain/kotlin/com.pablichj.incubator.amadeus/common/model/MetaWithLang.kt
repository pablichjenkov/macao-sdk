package com.pablichj.incubator.amadeus.common.model

import kotlinx.serialization.Serializable

@Serializable
data class MetaWithLang (
    val lang: String = "en"
)
