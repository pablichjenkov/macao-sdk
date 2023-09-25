package com.macaosoftware.component.topbar

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class TopBarComponentViewModel<T : TopBarStatePresenter>(
    protected val topBarComponent: TopBarComponent<T>,
    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always
) : ComponentViewModel() {

    abstract val topBarStatePresenter: T

    abstract fun onCreate()
    abstract fun mapComponentToStackBarItem(topComponent: Component): TopBarItem
    abstract fun onCheckChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
    abstract fun onBackstackEmpty()
}
