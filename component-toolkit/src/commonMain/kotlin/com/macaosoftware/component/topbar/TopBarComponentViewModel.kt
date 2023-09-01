package com.macaosoftware.component.topbar

import com.macaosoftware.component.core.Component

abstract class TopBarComponentViewModel<T : TopBarStatePresenter> {

    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always

    abstract fun create(topBarComponent: TopBarComponent<T>)
    abstract fun start()
    abstract fun stop()
    abstract fun destroy()
    abstract fun mapComponentToStackBarItem(topComponent: Component): TopBarItem
    abstract fun componentDelegateChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
}
