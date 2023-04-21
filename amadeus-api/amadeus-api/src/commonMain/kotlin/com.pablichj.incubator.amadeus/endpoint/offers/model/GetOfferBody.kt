package com.pablichj.incubator.amadeus.endpoint.offers.model

import com.pablichj.incubator.amadeus.common.model.MetaWithLang
import com.pablichj.incubator.amadeus.common.model.OfferData
import kotlinx.serialization.Serializable

@Serializable
class GetOfferBody(
    val data: OfferData,
    val meta: MetaWithLang
)
