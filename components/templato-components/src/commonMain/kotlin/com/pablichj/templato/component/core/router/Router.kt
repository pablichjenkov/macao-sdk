package com.pablichj.templato.component.core.router

interface Router {
    fun registerRoute(destination: DeepLinkDestination)
    fun unregisterRoute(destination: DeepLinkDestination)
    fun handleDeepLink(destination: String): DeepLinkResult
}