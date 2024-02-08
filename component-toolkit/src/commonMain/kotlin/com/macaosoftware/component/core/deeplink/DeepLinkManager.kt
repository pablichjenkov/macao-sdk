package com.macaosoftware.component.core.deeplink

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState

interface DeepLinkManager {
    fun navigateToDeepLink(
        component: Component,
        deepLinkMsg: DeepLinkMsg
    )
}

class DefaultDeepLinkManager: DeepLinkManager {

    override fun navigateToDeepLink(
        component: Component,
        deepLinkMsg: DeepLinkMsg
    ) {
        println("${component.instanceId()}::navigateToDeepLink(), path = ${deepLinkMsg.path.joinToString("/")}")

        if (component.lifecycleState != ComponentLifecycleState.Active) {
            println("${component.instanceId()}::navigateToDeepLink(), Waiting to be Started")
            component.deepLinkNavigationAwaitsStartedState = true
            component.awaitingDeepLinkMsg = deepLinkMsg
            return
        }

        val deepLinkPathSegment = deepLinkMsg.path[0]
        val match = component.deepLinkPathSegment == deepLinkPathSegment

        if (match) {
            if (deepLinkMsg.path.size == 1) {
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success(component))
                return
            }

            val nextDeepLinkPathSegment = deepLinkMsg.path[1]
            val nextComponent = component.getChildForNextUriFragment(nextDeepLinkPathSegment)
            if (nextComponent == null) {
                deepLinkMsg.resultListener.invoke(
                    DeepLinkResult.Error(
                        "Component: ${component.instanceId()} does not have any child that handle path segment = $nextDeepLinkPathSegment"
                    )
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
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success(nextComponent))
            }
        } else {
            deepLinkMsg.resultListener.invoke(
                DeepLinkResult.Error(
                    "Component: ${component.instanceId()} does not handle DeepLinkPathSegment = $deepLinkPathSegment."
                )
            )
        }
    }

}
