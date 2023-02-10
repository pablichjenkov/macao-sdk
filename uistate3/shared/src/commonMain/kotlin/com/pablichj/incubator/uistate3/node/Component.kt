package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.backstack.BackPressedCallback
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class Component : ComponentLifecycle {
    var parentComponent: Component? = null
    var lifecycleState: LifecycleState = LifecycleState.Created
    internal var rootBackPressedCallbackDelegate: BackPressedCallback? = null
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

    // region: Component Tree

    fun setParent(parentComponent: Component) {
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

    var treeContext: TreeContext? = null
    var deepLinkMatcher: ((String) -> Boolean)? = null

    protected open fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        return DeepLinkResult.Error(
            """
            $clazz::onDeepLinkMatch has been called but the function is not " +
                "override in this class. Default implementation does nothing.
            """
        )
    }

    protected open fun getDeepLinkSubscribedList(): List<Component> = emptyList()

    internal fun navigateToDeepLink(path: ArrayDeque<Component>): DeepLinkResult {

        val nextComponent = path.firstOrNull() ?: return DeepLinkResult.Success

        val matchingComponent = getDeepLinkSubscribedList().firstOrNull {
            it == nextComponent
        }
            ?: return DeepLinkResult.Error(
                """
                In the path, Component with clazz = $clazz could not find the required child
                Component with clazz = ${nextComponent.clazz} in the DeepLinkSubscribedList.
            """
            )

        val deepLinkResult = onDeepLinkNavigation(matchingComponent)

        return when (deepLinkResult) {
            is DeepLinkResult.Error -> {
                deepLinkResult
            }
            DeepLinkResult.Success -> {
                path.removeFirst()
                matchingComponent.navigateToDeepLink(path)
            }
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

interface ComponentLifecycle {
    fun start()
    fun stop()
    fun destroy()
}