package com.pablichj.templato.component.core.topbar

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.stack.StackBarItem

interface TopBarComponentDelegate<T : TopBarStatePresenter> {
    fun TopBarComponent<T>.create()
    fun TopBarComponent<T>.start()
    fun TopBarComponent<T>.stop()
    fun TopBarComponent<T>.destroy()
    fun mapComponentToStackBarItem(topComponent: Component): StackBarItem
    fun TopBarComponent<T>.componentDelegateChildForNextUriFragment(nextUriFragment: String): Component?
}
