package com.pablichj.encubator.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.navigation.DeepLinkResult
import com.pablichj.encubator.node.navigation.DefaultPathMatcher
import com.pablichj.encubator.node.navigation.IPathMatcher

abstract class Node(
    parentContext: NodeContext
) : Lifecycle {
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

    // region: DeepLink

    open fun onCheckChildMatchHandler(advancedPath: Path, matchingNode: Node): DeepLinkResult {
        return matchingNode.checkDeepLinkMatch(advancedPath)
    }

    open fun onNavigateChildMatchHandler(advancedPath: Path, matchingNode: Node): DeepLinkResult {
        onDeepLinkMatchingNode(matchingNode)
        return matchingNode.navigateUpToDeepLink(advancedPath)
    }

    open val pathMatcher: IPathMatcher by lazy {
        DefaultPathMatcher
    }

    protected open fun getDeepLinkNodes(): List<Node> = emptyList()

    protected open fun onDeepLinkMatchingNode(matchingNode: Node) {}

    fun handleDeepLink(path: Path): DeepLinkResult {
        return when (val deepLinkResult = checkDeepLinkMatch(path)) {
            is DeepLinkResult.Error -> {
                println(deepLinkResult.errorMsg)
                deepLinkResult
            }
            DeepLinkResult.Success -> {
                path.moveToStart()
                navigateUpToDeepLink(path)
            }
        }
    }

    internal fun checkDeepLinkMatch(path: Path): DeepLinkResult {
        return pathMatcher.traverseWithChildMatchAction(
            path,
            context.subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            onCheckChildMatchHandler(advancedPath, matchingNode)
        }
    }

    internal fun navigateUpToDeepLink(path: Path): DeepLinkResult {
        return pathMatcher.traverseWithChildMatchAction(
            path,
            context.subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            onNavigateChildMatchHandler(advancedPath, matchingNode)
        }
    }

    // endregion

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