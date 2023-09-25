package com.macaosoftware.component.stack

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class StackComponentViewModel<T : StackStatePresenter>(
    protected val stackComponent: StackComponent<T>
) : ComponentViewModel() {
    abstract val stackStatePresenter: T
    abstract fun onCreate()
    abstract fun onStackTopUpdate(topComponent: Component)
}