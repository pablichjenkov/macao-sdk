package com.pablichj.incubator.uistate3.node.navigation

interface Navigator {
    fun registerDestination(destination: DeepLinkDestination)
    fun unregisterDestination(destination: DeepLinkDestination)
    fun handleDeepLink(destination: String): DeepLinkResult
}