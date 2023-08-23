package com.pablichj.templato.component.core.stack

import com.pablichj.templato.component.core.Component

interface StackComponentDelegate {
    fun onStackTopUpdate(topComponent: Component)
}