package com.macaosoftware.component.topbar

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class TopBarComponentViewModel<T : TopBarStatePresenter> : ComponentViewModel() {

    open val showBackArrowStrategy: ShowBackArrowStrategy = ShowBackArrowStrategy.Always

    // TODO: Remove this, don't pass the component, the component should listen for
    // ViewModel events
    abstract fun create(topBarComponent: TopBarComponent<T>)
    abstract fun mapComponentToStackBarItem(topComponent: Component): TopBarItem
    abstract fun componentDelegateChildForNextUriFragment(
        nextUriFragment: String
    ): Component?
    abstract fun onBackstackEmpty()
}
