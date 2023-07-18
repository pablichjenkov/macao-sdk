package com.pablichj.templato.component.core.stack

import com.pablichj.templato.component.core.Component

interface PushStrategy<T: Component> {
    fun onPush(component: T, backStack: BackStack<T>)
}

class AddAllPushStrategy<T: Component> : PushStrategy<T> {
    override fun onPush(component: T, backStack: BackStack<T>) {
        val deque = backStack.deque
        val currentTopComponent = deque.lastOrNull()

        // If the same component on top is pushed again, a PushEqualTop event will be delivered.
        if (currentTopComponent == component) {
            backStack.onStackPushEqualTop()
            return
        }

        deque.addLast(component)
        backStack.onStackPush()
    }
}

class FixSizedPushStrategy<T: Component>(private val capacity: Int) : PushStrategy<T> {
    override fun onPush(component: T, backStack: BackStack<T>) {
        val deque = backStack.deque
        val currentTopComponent = deque.lastOrNull()

        // If the same component on top is pushed again, a PushEqualTop event will be delivered.
        if (currentTopComponent == component) {
            backStack.onStackPushEqualTop()
            return
        }

        if (deque.size == capacity) {
            deque.removeLast()
        }

        deque.addLast(component)
        backStack.onStackPush()
    }
}
