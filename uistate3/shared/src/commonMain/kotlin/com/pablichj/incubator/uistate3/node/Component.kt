package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.backstack.BackPressedCallback
import com.pablichj.incubator.uistate3.node.navigation.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class Component : Lifecycle {
    var parentComponent: Component? = null
    var lifecycleState: LifecycleState = LifecycleState.Created
    var subPath: SubPath = SubPath.Empty
    internal var rootBackPressedCallbackDelegate : BackPressedCallback? = null
    internal val clazz = this::class.simpleName

    internal val backPressedCallbackDelegate = object : BackPressedCallback() {
        override fun onBackPressed() {
            println("$clazz::onBackPressed() handling")
            handleBackPressed()
        }
    }

    private val _componentLifecycleFlow = MutableStateFlow<LifecycleState>(LifecycleState.Created)
    val componentLifecycleFlow: Flow<LifecycleState>
        get() = _componentLifecycleFlow

    // region: Tree

    fun attachToParent(parentComponent: Component) {
        if (this == parentComponent) throw IllegalArgumentException("A Node cannot be its parentNode")
        this.parentComponent = parentComponent
    }

    fun isAttached(): Boolean = parentComponent != null

    // endregion

    override fun start() {
        lifecycleState = LifecycleState.Started
        _componentLifecycleFlow.value = LifecycleState.Started
    }

    override fun stop() {
        lifecycleState = LifecycleState.Stopped
        _componentLifecycleFlow.value = LifecycleState.Stopped
    }

    override fun destroy() {
        lifecycleState = LifecycleState.Destroyed
        _componentLifecycleFlow.value = LifecycleState.Destroyed
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
        val parentNodeLocal = parentComponent
        if (parentNodeLocal != null) {
            println("$clazz::delegateBackPressedToParent()")
            parentNodeLocal.backPressedCallbackDelegate.onBackPressed()
        } else {
            // We have reached the root Component
            println("$clazz::delegateBackPressedInRootComponent()")
            rootBackPressedCallbackDelegate?.onBackPressed()
        }

    }

    // region: DeepLink

    open fun onCheckChildMatchHandler(advancedPath: Path, matchingComponent: Component): DeepLinkResult {
        return matchingComponent.checkDeepLinkMatch(advancedPath)
    }

    open fun onNavigateChildMatchHandler(advancedPath: Path, matchingComponent: Component): DeepLinkResult {
        onDeepLinkMatchingNode(matchingComponent)
        return matchingComponent.navigateUpToDeepLink(advancedPath)
    }

    open val pathMatcher: IPathMatcher by lazy {
        DefaultPathMatcher
    }

    protected open fun getDeepLinkNodes(): List<Component> = emptyList()

    protected open fun onDeepLinkMatchingNode(matchingComponent: Component) {}

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
        object Destroyed : LifecycleState
    }

}

interface Lifecycle {
    fun start()
    fun stop()
    fun destroy()
}