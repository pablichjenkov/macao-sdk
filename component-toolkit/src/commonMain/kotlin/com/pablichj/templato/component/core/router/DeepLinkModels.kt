package com.pablichj.templato.component.core.router

class DeepLinkMatchData(
    val uriFragment: String?,
    val matchType: DeepLinkMatchType
)

enum class DeepLinkMatchType {
    MatchOne,
    MatchAny
}
