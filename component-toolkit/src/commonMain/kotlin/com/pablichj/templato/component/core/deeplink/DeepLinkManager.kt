package com.pablichj.templato.component.core.deeplink

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState

interface DeepLinkManager {
    fun navigateToDeepLink(
        component: Component,
        deepLinkMsg: DeepLinkMsg
    )
}

class DefaultDeepLinkManager {

    fun navigateToDeepLink(
        component: Component,
        deepLinkMsg: DeepLinkMsg
    ) {
        println("${component.instanceId()}::navigateToDeepLink(), path = ${deepLinkMsg.path.joinToString("/")}")

        if (component.lifecycleState != ComponentLifecycleState.Started) {
            println("${component.instanceId()}::navigateToDeepLink(), Waiting to be Started")
            component.deepLinkNavigationAwaitsStartedState = true
            component.awaitingDeepLinkMsg = deepLinkMsg
            return
        }

        val uriFragment = deepLinkMsg.path[0]
        val match = component.uriFragment == uriFragment

        if (match) {
            if (deepLinkMsg.path.size == 1) {
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success, component)
                return
            }

            val nextUriFragment = deepLinkMsg.path[1]
            val nextComponent = component.getChildForNextUriFragment(nextUriFragment)
            if (nextComponent == null) {
                deepLinkMsg.resultListener.invoke(
                    DeepLinkResult.Error(
                        "Component: ${component.instanceId()} does not have any child that handle uri fragment = $nextUriFragment"
                    ),
                    null
                )
                return
            }

            nextComponent.startedFromDeepLink = true

            if (deepLinkMsg.path.size > 2) {
                val nextDeepLinkMsg = deepLinkMsg.copy(
                    path = deepLinkMsg.path.subList(1, deepLinkMsg.path.size)
                )
                component.onDeepLinkNavigateTo(nextComponent)
                navigateToDeepLink(nextComponent, nextDeepLinkMsg)
            } else {
                component.onDeepLinkNavigateTo(nextComponent)
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success, nextComponent)
            }
        } else {
            deepLinkMsg.resultListener.invoke(
                DeepLinkResult.Error(
                    "Component: ${component.instanceId()} does not handle DeepLink fragment = $uriFragment."
                ),
                null
            )
        }
    }

}
