package com.macaosoftware.component.core

import com.macaosoftware.component.stack.BackStack
import com.macaosoftware.component.stack.PushStrategy

interface ComponentWithBackStack : ComponentWithChildren {
    val backStack: BackStack<Component>

    fun createBackStack(pushStrategy: PushStrategy<Component>): BackStack<Component> {
        return BackStack(pushStrategy)
    }
}