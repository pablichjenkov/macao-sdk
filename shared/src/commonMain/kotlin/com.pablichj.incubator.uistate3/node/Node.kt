package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import com.pablichj.incubator.uistate3.node.navigation.DefaultPathMatcher
import com.pablichj.incubator.uistate3.node.navigation.IPathMatcher
import com.pablichj.incubator.uistate3.node.navigation.Path

abstract class Node : Lifecycle {
    val context: NodeContext = NodeContext()
    private val clazz = this::class.simpleName

    protected val backPressedCallbackHandler = object : BackPressedCallback() {
        override fun onBackPressed() {
            println("$clazz::onBackPressed() called")
            handleBackPressed()
        }
    }

    init {
        context.backPressedCallbackDelegate = backPressedCallbackHandler
    }

    override fun start() {
        context.lifecycleState = LifecycleState.Started
        /*backPressDispatcher()?.run {
            println("Subscribing to backPressDispatcher, class = ${this@Node::class.simpleName}")
            subscribe(backPressedCallback)
        }*/
    }

    override fun stop() {
        context.lifecycleState = LifecycleState.Stopped
        /*backPressDispatcher()?.run {
            println("Unsubscribing to backPressDispatcher, class = ${this@Node::class.simpleName}")
            unsubscribe(backPressedCallback)
        }*/
    }

    /**
     * Default implementation for a back press event. It does nothing, just forward it
     * to the parent.
     * */
    protected open fun handleBackPressed() {
        delegateBackPressedToParent()
    }

    protected fun delegateBackPressedToParent() {
        val parentContext = context.parentContext
        if (parentContext != null) {
            println("$clazz::delegateBackPressedToParent()")
            parentContext.backPressedCallbackDelegate.onBackPressed()
        } else {
            println("$clazz::delegateBackPressedToRootFinal()")
            context.rootNodeBackPressedDelegate?.onBackPressed()
        }

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