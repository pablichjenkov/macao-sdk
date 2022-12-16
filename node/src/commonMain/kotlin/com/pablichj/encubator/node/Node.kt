package com.pablichj.encubator.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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

    protected open fun getDeepLinkNodes(): List<Node> = emptyList()

    protected open fun onDeepLinkMatchingNode(matchingNode: Node) {}

    // Give a chance to the subclass to override the selected node. Good for cases like the
    // Adaptable windows, where the current node is always returned
    protected open fun onInterceptMatchingNode(matchingNode: Node): Node {
        return matchingNode
    }

    fun handleDeepLink(path: Path): DeepLinkResult {
        return when (val deepLinkResult = checkDeepLinkMatch(path)) {
            is DeepLinkResult.Error -> {
                println("Error matching deep link path: ${deepLinkResult.errorMsg}")
                deepLinkResult
            }
            DeepLinkResult.Success -> {
                path.moveToStart()
                navigateUpToDeepLink(path)
            }
        }
    }

    internal fun checkDeepLinkMatch(path: Path): DeepLinkResult {
        return PathMatcher.traverseWithChildMatchAction(
            path,
            context.subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            val interceptNode = onInterceptMatchingNode(matchingNode)
            interceptNode.checkDeepLinkMatch(advancedPath)
        }
    }

    internal fun navigateUpToDeepLink(path: Path): DeepLinkResult {
        return PathMatcher.traverseWithChildMatchAction(
            path,
            context.subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            val interceptNode = onInterceptMatchingNode(matchingNode)
            // Let subclasses know about the match so they push the matching child to their stack
            onDeepLinkMatchingNode(interceptNode)
            interceptNode.navigateUpToDeepLink(advancedPath)
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