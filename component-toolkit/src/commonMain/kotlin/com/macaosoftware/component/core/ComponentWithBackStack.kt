package com.macaosoftware.component.core

import com.macaosoftware.component.stack.BackStack
import com.macaosoftware.component.stack.BackstackRecords
import com.macaosoftware.component.stack.PushStrategy

interface ComponentWithBackStack : ComponentWithChildrenOneActive {
    val backStack: BackStack<Component>
    val navigator: Navigator
    val backstackRecords: BackstackRecords

    fun createBackStack(pushStrategy: PushStrategy<Component>): BackStack<Component> {
        return BackStack(pushStrategy)
    }
}
