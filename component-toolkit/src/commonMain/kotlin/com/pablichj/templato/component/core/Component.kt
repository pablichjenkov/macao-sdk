package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.router.DeepLinkMsg
import com.pablichj.templato.component.core.router.DeepLinkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Component : ComponentLifecycle() {

    // region: Component Tree

    var parentComponent: Component? = null

    fun setParent(parentComponent: Component) {
        if (this == parentComponent) throw IllegalArgumentException("A Component cannot be its own parent Component")
        this.parentComponent = parentComponent
    }

    fun isAttached(): Boolean = parentComponent != null

    // endregion

    // region: Lifecycle

    var lifecycleState: ComponentLifecycleState = ComponentLifecycleState.Created

    private val _lifecycleStateFlow =
        MutableStateFlow<ComponentLifecycleState>(ComponentLifecycleState.Created)
    val lifecycleStateFlow: StateFlow<ComponentLifecycleState>
        get() = _lifecycleStateFlow.asStateFlow()

    open fun dispatchStart() {
        lifecycleState =
            ComponentLifecycleState.Started // It has to be the first line of this block
        if (deepLinkNavigationAwaitsStartedState) {
            deepLinkNavigationAwaitsStartedState = false
            awaitingDeepLinkMsg?.let { navigateToDeepLink(it) }
        }
        onStart()
        _lifecycleStateFlow.value = ComponentLifecycleState.Started
    }

    open fun dispatchStop() {
        lifecycleState = ComponentLifecycleState.Stopped
        onStop()
        _lifecycleStateFlow.value = ComponentLifecycleState.Stopped
    }

    open fun dispatchDestroy() {
        lifecycleState = ComponentLifecycleState.Destroyed
        onDestroy()
        _lifecycleStateFlow.value = ComponentLifecycleState.Destroyed
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    // endregion

    // region: BackPress

    /**
     * If a Component does not override handleBackPressed() function, the default behavior is to
     * delegate/forward the back press event upstream, for its parent Component to handle it.
     * All the way up to the root Component.
     * */
    open fun handleBackPressed() {
        println("${instanceId()}::onBackPressed() handling")
        delegateBackPressedToParent()
    }

    protected fun delegateBackPressedToParent() {
        val parentComponentCopy = parentComponent
        if (parentComponentCopy != null) {
            println("${instanceId()}::delegateBackPressedToParent()")
            parentComponentCopy.handleBackPressed()
        } else {
            println("${instanceId()}::Back Press reached root component unhandled")
        }
    }

    // endregion

    // region: DeepLink

    private var deepLinkNavigationAwaitsStartedState = false
    private var awaitingDeepLinkMsg: DeepLinkMsg? = null
    open var uriFragment: String? = null

    fun navigateToDeepLink(
        deepLinkMsg: DeepLinkMsg
    ) {
        println("${instanceId()}::navigateToDeepLink(), path = ${deepLinkMsg.path.joinToString("/")}")

        if (lifecycleState != ComponentLifecycleState.Started) {
            println("${instanceId()}::navigateToDeepLink(), Waiting to be Started ")
            deepLinkNavigationAwaitsStartedState = true
            awaitingDeepLinkMsg = deepLinkMsg
            return
        }

        val uriFragment = deepLinkMsg.path[0]
        val match = this.uriFragment == uriFragment

        if (match) {
            if (deepLinkMsg.path.size == 1) {
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success)
                return
            }

            val nextUriFragment = deepLinkMsg.path[1]
            val nextComponent = getChildForNextUriFragment(nextUriFragment)
            if (nextComponent == null) {
                deepLinkMsg.resultListener.invoke(
                    DeepLinkResult.Error(
                        "Component: ${instanceId()} does not have any child that handle uri fragment = $nextUriFragment"
                    )
                )
                return
            }

            if (deepLinkMsg.path.size > 2) {
                val nextDeepLinkMsg = deepLinkMsg.copy(
                    path = deepLinkMsg.path.subList(1, deepLinkMsg.path.size)
                )
                onDeepLinkNavigation(nextComponent)
                nextComponent.navigateToDeepLink(nextDeepLinkMsg)
            } else {
                onDeepLinkNavigation(nextComponent)
                deepLinkMsg.resultListener.invoke(DeepLinkResult.Success)
            }
        } else {
            deepLinkMsg.resultListener.invoke(
                DeepLinkResult.Error("Component: ${instanceId()} does not handle DeepLink fragment = $uriFragment.")
            )
        }
    }

    open fun getChildForNextUriFragment(
        nextUriFragment: String
    ): Component? {
        println("${instanceId()}::getChildForNextUriFragment() has been called but the function is not override in this class")
        return null
    }

    protected open fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        return DeepLinkResult.Error(
            """
            ${instanceId()}::onDeepLinkMatch has been called but the function is not " +
                "override in this class. Default implementation does nothing.
            """
        )
    }

    // endregion

    // region: Composable Content

    @Composable
    abstract fun Content(modifier: Modifier)

    // endregion

    // region: Debugging

    open fun instanceId(): String {
        val addressLast3 = this.toString().let { it.substring(it.length - 3) }
        return "${this::class.simpleName}_${addressLast3}"
    }

    // endregion
}

sealed interface ComponentLifecycleState {
    object Created : ComponentLifecycleState
    object Started : ComponentLifecycleState
    object Stopped : ComponentLifecycleState
    object Destroyed : ComponentLifecycleState
}

abstract class ComponentLifecycle {
    protected abstract fun onStart()
    protected abstract fun onStop()
    protected abstract fun onDestroy()
}
