package com.pablichj.templato.component.core.stack

import com.pablichj.templato.component.core.Component

interface StackComponentDelegate<T : StackStatePresenter> {
    fun onStackTopUpdate(topComponent: Component)
}