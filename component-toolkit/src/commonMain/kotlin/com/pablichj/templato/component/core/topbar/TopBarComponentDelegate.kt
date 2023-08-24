package com.pablichj.templato.component.core.topbar

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.stack.StackBarItem

interface TopBarComponentDelegate {
    fun TopBarComponent<*>.create()
    fun TopBarComponent<*>.start()
    fun TopBarComponent<*>.stop()
    fun mapComponentToStackBarItem(topComponent: Component): StackBarItem
    fun TopBarComponent<*>.childForNextUriFragment(nextUriFragment: String): Component?
    fun TopBarComponent<*>.deepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        println("${getComponent().instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
        backStack.push(matchingComponent)
        return DeepLinkResult.Success
    }
}
