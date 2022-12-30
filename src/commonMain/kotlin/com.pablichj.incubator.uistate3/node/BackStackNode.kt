package com.pablichj.incubator.uistate3.node

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue

/**
 * A stack of references to Node instances. Can be inherited by a Child class
 * that wants to manage children navigation.
 * */
abstract class BackStackNode<T : Node>(
    parentContext: NodeContext
) : Node(parentContext) {

    val stack: ArrayDeque<T> = ArrayDeque()
//    var screenUpdateCounter by mutableStateOf(0)
    /**
     * Push a Node to the top of the stack.
     * When a Node is push successfully, a callback function will be called in subclasses.
     * */
    protected fun pushNode(node: T) {
        if (stack.size == 0) {
            stack.addLast(node)
            onStackPush(oldTop = null, newTop = node )
            return
        }

        val currentNode = stack.lastOrNull()

        // No action if the same node is pushed
        if (currentNode == node) {
            return
        }

        stack.addLast(node)
        onStackPush(oldTop = currentNode, newTop = node)
    }


    /**
     * Remove the top most Node from the stack.
     * When a Node is pop successfully, a callback function will be called in subclasses.
     * */
    protected fun popNode() {
        val oldTop = stack.removeLast()
        val newTop = if (stack.size > 0) {
            stack.lastOrNull()
        } else null
        onStackPop(oldTop = oldTop,  newTop = newTop)
    }

    protected fun popToNode(node: T, inclusive: Boolean): Boolean {

        val shouldPop: Boolean = stack.lastIndexOf(node) != -1
        if (!shouldPop) {
            return false
        }

        val oldTop = stack.last()
        if (oldTop == node && !inclusive) {
            return false
        }

        var popping = oldTop != node
        while (popping) {
            stack.removeLast()
            popping = stack.lastOrNull() != node
        }

        if (inclusive) {
            stack.removeLast()
        }

        onStackPop(oldTop = oldTop, newTop = stack.lastOrNull())
        return true
    }

    fun popToIndex(popToIndex: Int): Boolean {
        var poppingIndex = stack.lastIndex
        if (poppingIndex <= popToIndex) {
            return false
        }

        val currentTop = stack.removeLast()
        poppingIndex--

        while (poppingIndex > popToIndex) {
            stack.removeAt(poppingIndex)
            poppingIndex--
        }

        onStackPop(oldTop = currentTop, newTop = stack.lastOrNull())
        return true
    }

    override fun handleBackPressed() {
        println("BackStack::handleBackPressed in class ${this@BackStackNode::class.simpleName}," +
                " size = ${stack.size}")
        if (stack.size > 1) {
            popNode()
        } else {
            // We delegate the back event when the stack has 1 element and not 0. The reason is if
            // we pop all the way to zero the stack empty view will be show for a fraction of
            // milliseconds and this creates an undesirable effect.
            delegateBackPressedToParent()
        }
    }

    /**
     * The reason oldTop is null is because the first time a node is pushed, there is no previous
     * element in the stack.
     **/
    abstract fun onStackPush(oldTop: T?, newTop: T)

    /**
     * The reason newTop is null is because the last time a node is popped, there is no previous
     * top element in the stack.
     **/
    abstract fun onStackPop(oldTop: T, newTop: T?)

    //abstract fun onStackPopManySuccess()
}