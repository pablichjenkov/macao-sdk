package com.pablichj.incubator.uistate3.node.navigation

interface Navigator {
    fun registerDestination(destination: Destination)
    fun unregisterDestination(destination: Destination)
    fun handleDeepLink(destination: String): DeepLinkResult
}