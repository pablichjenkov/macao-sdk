package com.pablichj.templato.component.core.topbar

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.deeplink.DeepLinkResult


interface TopBarComponentDelegate {
    fun TopBarComponent<*>.create()
    fun TopBarComponent<*>.start()
    fun TopBarComponent<*>.stop()
    fun TopBarComponent<*>.childForNextUriFragment(nextUriFragment: String): Component?
    fun TopBarComponent<*>.deepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        println("${getComponent().instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
        backStack.push(matchingComponent)
        return DeepLinkResult.Success
    }
}