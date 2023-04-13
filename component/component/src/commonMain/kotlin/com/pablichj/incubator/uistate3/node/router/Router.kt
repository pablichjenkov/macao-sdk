package com.pablichj.incubator.uistate3.node.router

interface Router {
    fun registerRoute(destination: DeepLinkDestination)
    fun unregisterRoute(destination: DeepLinkDestination)
    fun handleDeepLink(destination: String): DeepLinkResult
}