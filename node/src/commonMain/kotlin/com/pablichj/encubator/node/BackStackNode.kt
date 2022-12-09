package com.pablichj.encubator.node

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.util.*

/**
 * A stack of references to Node instances. Can be inherited by a Child class
 * that wants to manage children navigation.
 * */
open class BackStackNode<T : Node>(parentContext: NodeContext) : Node(parentContext) {

    val stack: Stack<T> = Stack()
    var screenUpdateCounter by mutableStateOf(0)

    /**
     * Push a Node to the top of the stack.
     * When a Node is push successfully, the start() function will be called on it.
     * */
    protected fun pushNode(node: T) {

        if (stack.size == 0) {
            stack.push(node).also { node.start() }
            updateScreen()
            return
        }

        val currentNode = stack.peek()
        if (currentNode == node) {
            updateScreen()
            return
        }

        currentNode.stop()
        stack.push(node).also { node.start() }

        updateScreen()
    }

    /**
     * Remove the top most Node from the stack.
     * When a Node is pop successfully, the stop() function will be called on it.
     * */
    protected fun popNode(): Boolean {
        return if (stack.size > 0) {
            val poppingNode = stack.pop()
            poppingNode.stop()
            // After popping the current node, the coming one needs start() called on it
            if (stack.size > 0) {
                stack.peek().start()
            }
            updateScreen()
            true
        } else false
    }

    protected fun popToNode(node: T, inclusive: Boolean): Boolean {

        val shouldPop: Boolean = stack.search(node) != -1
        if (!shouldPop) {
            return false
        }

        var popping = stack.peek() != node
        while (popping) {
            stack.pop()
            popping = stack.peek() != node
        }

        if (inclusive) {
            stack.pop()
        }

        updateScreen()
        return true
    }

    fun popToIndex(popToIndex: Int): Boolean {
        var poppingIndex = stack.lastIndex
        if (poppingIndex <= popToIndex) {
            return false
        }

        // When removing the current active Node we gotta call stop() o it. Only for this Node,
        // the rest of the Stack should have been Stopped already.
        val currentStartedNode = stack.pop()
        currentStartedNode.stop()
        poppingIndex--

        while (poppingIndex > popToIndex) {
            stack.removeAt(poppingIndex)
            poppingIndex--
        }

        updateScreen()
        return true
    }

    override fun handleBackPressed() {
        println("BackStack::handleBackPressed in class ${this.javaClass.simpleName}, size = ${stack.size}")
        if (stack.size > 1) {
            popNode()
        } else {
            delegateBackPressedToParent()
        }
    }

    // TODO: Potentially make this function abstract
    protected open fun onStackTopChanged(node: T) {
        throw NotImplementedError(
            """You have to override function onStackTopChanged()
                | in class ${this.javaClass.simpleName}
                | and do not call super.onStackTopChanged(node)
            """.trimMargin()
        )
    }

    private fun updateScreen() {
        if (stack.size > 0) {
            // TODO: Look for another method to trigger the screen update, try the node reference itself
            if (screenUpdateCounter == Int.MAX_VALUE) {
                screenUpdateCounter = 0
            } else {
                screenUpdateCounter ++
            }
            onStackTopChanged(stack.peek())
        }
    }

    // TODO: Potentially make this function abstract
    @Composable
    override fun Content(modifier: Modifier) {
        println("BackStackNavigatorNode::Composing BackStackNavigatorNode.Content stackSize = ${stack.size}")

        Box(modifier = Modifier.fillMaxSize()) {
            if (screenUpdateCounter >= 0 && stack.size > 0) {
                stack.peek().Content(Modifier)
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                    text = "Empty Stack, Please add some children",
                    textAlign = TextAlign.Center
                )
            }
        }

    }

}