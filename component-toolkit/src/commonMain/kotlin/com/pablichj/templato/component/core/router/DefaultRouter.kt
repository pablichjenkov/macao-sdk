package com.pablichj.templato.component.core.router

import com.pablichj.templato.component.core.InternalRootComponent

internal class DefaultRouter(
    private val rootComponent: InternalRootComponent
) : Router {

    override fun handleDeepLink(
        deepLinkMsg: DeepLinkMsg
    ) {
        println("DefaultRouter::handleDeepLink2 = ${deepLinkMsg.path.joinToString("/")}")
        if (deepLinkMsg.path.isEmpty()) {
            deepLinkMsg.resultListener.invoke(
                DeepLinkResult.Error(
                    "path cannot be empty"
                )
            )
        }
        rootComponent.navigateToDeepLink(deepLinkMsg)
    }

}
