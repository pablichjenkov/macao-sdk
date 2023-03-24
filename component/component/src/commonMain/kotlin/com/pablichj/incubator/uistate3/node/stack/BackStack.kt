package com.pablichj.incubator.uistate3.node.stack

import com.pablichj.incubator.uistate3.node.Component

/**
 * A stack of references to Node instances. Can be inherited by a Child class
 * that wants to manage children navigation.
 * */
class BackStack<T : Component> {
    internal val deque: ArrayDeque<T> = ArrayDeque()
    var eventListener: (event: Event<T>) -> Unit = {}

    /**
     * Push a Node to the top of the stack.
     * When a Node is push successfully, a Push event will be delivered.
     * */
    fun push(node: T) {
        val currentTopNode = deque.lastOrNull()

        // If the same node on top is pushed again, a PushEqualTop event will be delivered.
        if (currentTopNode == node) {
            onStackPushEqualTop()
            return
        }

        deque.addLast(node)
        onStackPush()
    }

    /**
     * Remove the top most Node from the stack.
     * When a Node is pop successfully, a Pop event will be delivered.
     * */
    fun pop() {
        if (deque.size == 0) {
            eventListener(Event.PopEmptyStack())
            return
        }
        val oldTop = deque.removeLast()
        onStackPop(oldTop)
    }

    fun popTo(node: T, inclusive: Boolean): Boolean {

        val shouldPop: Boolean = deque.lastIndexOf(node) != -1
        if (!shouldPop) {
            return false
        }

        val oldTop = deque.last()
        if (oldTop == node && !inclusive) {
            return false
        }

        var popping = oldTop != node
        while (popping) {
            deque.removeLast()
            popping = deque.lastOrNull() != node
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