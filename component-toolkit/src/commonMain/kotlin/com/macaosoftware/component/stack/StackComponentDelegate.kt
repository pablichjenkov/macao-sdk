package com.macaosoftware.component.stack

import com.macaosoftware.component.core.Component

interface StackComponentDelegate<T : StackStatePresenter> {
    fun onStackTopUpdate(topComponent: Component)
}