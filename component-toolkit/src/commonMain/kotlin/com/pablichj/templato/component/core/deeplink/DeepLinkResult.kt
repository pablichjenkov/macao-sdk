package com.pablichj.templato.component.core.deeplink

sealed class DeepLinkResult {
    object Success : DeepLinkResult()
    data class Error(val errorMsg: String) : DeepLinkResult()
}

data class ComponentConnection(
    var request: Any? = null,
    var response: Any? = null
)