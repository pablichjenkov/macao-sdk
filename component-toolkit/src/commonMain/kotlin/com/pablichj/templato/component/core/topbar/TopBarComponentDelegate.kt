package com.pablichj.templato.component.core.topbar

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem

abstract class TopBarComponentDelegate<T : TopBarStatePresenter> {

    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always

    abstract fun TopBarComponent<T>.create()
    abstract fun TopBarComponent<T>.start()
    abstract fun TopBarComponent<T>.stop()
    abstract fun TopBarComponent<T>.destroy()
    abstract fun mapComponentToStackBarItem(topComponent: Component): StackBarItem
    abstract fun TopBarComponent<T>.componentDelegateChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
}
