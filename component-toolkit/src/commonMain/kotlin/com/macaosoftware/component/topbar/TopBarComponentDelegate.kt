package com.macaosoftware.component.topbar

import com.macaosoftware.component.core.Component

abstract class TopBarComponentDelegate<T : TopBarStatePresenter> {

    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always

    abstract fun TopBarComponent<T>.create()
    abstract fun TopBarComponent<T>.start()
    abstract fun TopBarComponent<T>.stop()
    abstract fun TopBarComponent<T>.destroy()
    abstract fun mapComponentToStackBarItem(topComponent: Component): TopBarItem
    abstract fun TopBarComponent<T>.componentDelegateChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
}
