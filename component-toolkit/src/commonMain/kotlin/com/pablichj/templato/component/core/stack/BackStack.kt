package com.pablichj.templato.component.core.stack

import com.pablichj.templato.component.core.Component

/**
 * A stack of references to Component instances. Can be inherited by a Child class
 * that wants to manage children navigation.
 * */
class BackStack<T : Component> {
    internal val deque: ArrayDeque<T> = ArrayDeque()
    var eventListener: (event: Event<T>) -> Unit = {}

    /**
     * Push a Component to the top of the stack.
     * When a Component is push successfully, a Push event will be delivered.
     * */
    fun push(component: T) {
        val currentTopComponent = deque.lastOrNull()

        // If the same component on top is pushed again, a PushEqualTop event will be delivered.
        if (currentTopComponent == component) {
            onStackPushEqualTop()
            return
        }

        deque.addLast(component)
        onStackPush()
    }

    /**
     * Remove the top most Component from the stack.
     * When a Component is pop successfully, a Pop event will be delivered.
     * */
    fun pop() {
        if (deque.size == 0) {
            eventListener(Event.PopEmptyStack())
            return
        }
        val oldTop = deque.removeLast()
        onStackPop(oldTop)
    }

    fun popTo(component: T, inclusive: Boolean): Boolean {

        val shouldPop: Boolean = deque.lastIndexOf(component) != -1
        if (!shouldPop) {
            return false
        }

        val oldTop = deque.last()
        if (oldTop == component && !inclusive) {
            return false
        }

        var popping = oldTop != component
        while (popping) {
            deque.removeLast()
            popping = deque.lastOrNull() != component
        }

        if (inclusive) {
            deque.removeLast()
        }

        onStackPop(oldTop)
        return true
    }

    fun popToIndex(popToIndex: Int): Boolean {
        var poppingIndex = deque.lastIndex
        if (poppingIndex <= popToIndex) {
            return false
        }

        val oldTop = deque.removeLast()
        poppingIndex--

        while (poppingIndex > popToIndex) {
            deque.removeAt(poppingIndex)
            poppingIndex--
        }

        onStackPop(oldTop)
        return true
    }

    private fun onStackPush() {
        eventListener(Event.Push(deque))
    }

    private fun onStackPushEqualTop() {
        eventListener(Event.PushEqualTop(deque))
    }

    private fun onStackPop(oldTop: T) {
        eventListener(Event.Pop(deque, oldTop))
    }

    fun size(): Int {
        return deque.size
    }

    fun clear() {
        deque.clear()
    }


    sealed class Event<T> {
        class Push<T>(val stack: List<T>) : Event<T>()
        class Pop<T>(val stack: List<T>, val oldTop: T) : Event<T>()

        //class PopMany<T>(val stack: List<T>, val oldTop: T) : Event<T>()
        class PushEqualTop<T>(val stack: List<T>) : Event<T>()
        class PopEmptyStack<T> : Event<T>()
    }

}