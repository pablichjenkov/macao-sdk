package com.pablichj.incubator.uistate3.node.router

sealed class DeepLinkResult {
    object Success : DeepLinkResult()
    data class Error(val errorMsg: String) : DeepLinkResult()
}