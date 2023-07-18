package com.pablichj.templato.component.core

import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.stack.PushStrategy

interface ComponentWithBackStack : ComponentWithChildren {
    val backStack: BackStack<Component>

    fun createBackStack(pushStrategy: PushStrategy<Component>): BackStack<Component> {
        return BackStack(pushStrategy)
    }
}