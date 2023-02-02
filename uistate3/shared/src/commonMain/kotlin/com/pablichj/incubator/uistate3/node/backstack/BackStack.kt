package com.pablichj.incubator.uistate3.node.backstack

import com.pablichj.incubator.uistate3.node.Component
import kotlinx.coroutines.flow.*

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue

/**
 * A stack of references to Node instances. Can be inherited by a Child class
 * that wants to manage children navigation.
 * */
class BackStack<T : Component> {
    internal val deque: ArrayDeque<T> = ArrayDeque()

    /**
     * Push a Node to the top of the stack.
     * When a Node is push successfully, a Push event will be delivered.
     * */
    internal fun push(node: T) {
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
    internal fun pop() {
        if (deque.size == 0) {
            eventListener(Event.PopEmptyStack())
            return
        }
        val oldTop = deque.removeLast()
        onStackPop(oldTop)
    }

    internal fun popTo(node: T, inclusive: Boolean): Boolean {

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

    internal fun size(): Int {
        return deque.size
    }

    internal fun clear() {
        deque.clear()
    }

    var eventListener: (event: Event<T>) -> Unit = {}

    /**
     * The reason oldTop is null is because the first time a node is pushed, there is no previous
     * element in the stack.
     **/
    //abstract fun onStackPush(oldTop: T?, newTop: T)

    /**
     * The reason newTop is null is because the last time a node is popped, there is no previous
     * top element in the stack.
     **/
    //abstract fun onStackPop(oldTop: T, newTop: T?)

    //abstract fun onStackPopManySuccess()

    sealed class Event<T> {
        class Push<T>(val stack: List<T>) : Event<T>()
        class Pop<T>(val stack: List<T>, val oldTop: T) : Event<T>()
        class PushEqualTop<T>(val stack: List<T>) : Event<T>()
        class PopEmptyStack<T> : Event<T>()
    }
}