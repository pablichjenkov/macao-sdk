package com.macaosoftware.component.stack

import com.macaosoftware.component.core.Component

interface StackComponentViewModel<T : StackStatePresenter> {
    fun onStackTopUpdate(topComponent: Component)
}