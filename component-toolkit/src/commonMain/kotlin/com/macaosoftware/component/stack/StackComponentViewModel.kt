package com.macaosoftware.component.stack

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.viewmodel.ComponentViewModel

abstract class  StackComponentViewModel<T : StackStatePresenter>: ComponentViewModel() {
    abstract fun create(stackComponent: StackComponent<T>)
    abstract fun onStackTopUpdate(topComponent: Component)
}