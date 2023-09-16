package com.macaosoftware.component.topbar

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class TopBarComponentViewModel<T : TopBarStatePresenter> : ComponentViewModel() {

    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always

    abstract fun onCreate(topBarComponent: TopBarComponent<T>)
    abstract fun mapComponentToStackBarItem(topComponent: Component): TopBarItem
    abstract fun onCheckChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
    abstract fun onBackstackEmpty()
}
