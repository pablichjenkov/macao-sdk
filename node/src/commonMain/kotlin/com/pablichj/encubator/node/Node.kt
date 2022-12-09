package com.pablichj.encubator.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

abstract class Node(
    parentContext: NodeContext
) : Lifecycle {

    var id: Int = 0
    val context: NodeContext = NodeContext(parentContext)

    private val backPressedCallback = object : BackPressedCallback() {
        override fun onBackPressed() {
            println("Node::onBackPressed() called, class = ${this@Node.javaClass.simpleName}")
            handleBackPressed()
        }
    }

    init {
        context.backPressedCallbackDelegate = backPressedCallback
    }

    override fun start() {
        context.lifecycleState = LifecycleState.Started
        backPressDispatcher()?.run {
            println("Subscribing to backPressDispatcher, class = ${this@Node.javaClass.simpleName}")
            subscribe(backPressedCallback)
        }
    }

    override fun stop() {
        context.lifecycleState = LifecycleState.Stopped
        backPressDispatcher()?.run {
            println("Unsubscribing to backPressDispatcher, class = ${this@Node.javaClass.simpleName}")
            unsubscribe(backPressedCallback)
        }
    }

    private fun backPressDispatcher(): IBackPressDispatcher? {
        return context.findBackPressDispatcher()
    }

    /**
     * Default implementation for a back press event. It does nothing, just forward it
     * to the parent.
     * */
    protected open fun handleBackPressed() {
        delegateBackPressedToParent()
    }

    protected fun delegateBackPressedToParent() {
        println("Node::delegateBackPressedToParent()")
        context.parentContext?.backPressedCallbackDelegate?.onBackPressed()
    }

    @Composable
    abstract fun Content(modifier: Modifier)

    sealed interface LifecycleState {
        object Created : LifecycleState
        object Started : LifecycleState
        object Stopped : LifecycleState
    }

}

interface Lifecycle {
    fun start()
    fun stop()
}