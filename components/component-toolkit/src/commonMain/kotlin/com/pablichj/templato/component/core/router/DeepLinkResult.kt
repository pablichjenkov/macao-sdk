package com.pablichj.templato.component.core.router

sealed class DeepLinkResult {
    object Success : DeepLinkResult()
    data class Error(val errorMsg: String) : DeepLinkResult()
}