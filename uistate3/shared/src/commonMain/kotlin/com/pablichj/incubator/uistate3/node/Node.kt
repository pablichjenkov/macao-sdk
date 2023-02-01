package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.navigation.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class Node : Lifecycle {
    var parentNode: Node? = null
    var lifecycleState: Node.LifecycleState = Node.LifecycleState.Created
    var subPath: SubPath = SubPath.Empty
    internal var rootNodeBackPressedDelegate : BackPressedCallback? = null
    internal val clazz = this::class.simpleName

    internal val backPressedCallbackHandler = object : BackPressedCallback() {
        override fun onBackPressed() {
            println("$clazz::onBackPressed() handling")
            handleBackPressed()
        }
    }

    private val _nodeLifecycleFlow = MutableStateFlow<LifecycleState>(LifecycleState.Created)
    val nodeLifecycleFlow: Flow<LifecycleState>
        get() = _nodeLifecycleFlow

    init {
    }

    // region: Tree

    fun attachToParent(parentNode: Node) {
        if (this == parentNode) throw IllegalArgumentException("A Node cannot be its parentNode")
        this.parentNode = parentNode
    }

    fun isAttached(): Boolean = parentNode != null

    // endregion

    override fun start() {
        lifecycleState = LifecycleState.Started
        _nodeLifecycleFlow.value = LifecycleState.Started
    }

    override fun stop() {
        lifecycleState = LifecycleState.Stopped
        _nodeLifecycleFlow.value = LifecycleState.Stopped
    }

    /**
     * If a Component does not override handleBackPressed() function, the default behavior is to
     * delegate/forward the back press event upstream, for its parent Component to handle it.
     * All the way up to the root Component.
     * */
    protected open fun handleBackPressed() {
        delegateBackPressedToParent()
    }

    protected fun delegateBackPressedToParent() {
        val parentNodeLocal = parentNode
        if (parentNodeLocal != null) {
            println("$clazz::delegateBackPressedToParent()")
            parentNodeLocal.backPressedCallbackHandler.onBackPressed()
        } else {
            // We have reached the root Component
            println("$clazz::delegateBackPressedInRootComponent()")
            rootNodeBackPressedDelegate?.onBackPressed()
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
            subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            onCheckChildMatchHandler(advancedPath, matchingNode)
        }
    }

    internal fun navigateUpToDeepLink(path: Path): DeepLinkResult {
        return pathMatcher.traverseWithChildMatchAction(
            path,
            subPath,
            getDeepLinkNodes()
        ) { advancedPath, matchingNode ->
            onNavigateChildMatchHandler(advancedPath, matchingNode)
        }
    }

    // endregion

    @Composable
    internal abstract fun Content(modifier: Modifier)

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