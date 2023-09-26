package com.macaosoftware.component.stack

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class StackComponentViewModel(
    protected val stackComponent: StackComponent<StackComponentViewModel>
) : ComponentViewModel() {
    abstract val stackStatePresenter: StackStatePresenter
    abstract fun onCreate()
    abstract fun onStackTopUpdate(topComponent: Component)
}