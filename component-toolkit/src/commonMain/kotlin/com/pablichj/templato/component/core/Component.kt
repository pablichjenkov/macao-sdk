package com.pablichj.templato.component.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.deeplink.DeepLinkManager
import com.pablichj.templato.component.core.deeplink.DeepLinkMsg
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.deeplink.DefaultDeepLinkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Component : ComponentLifecycle() {

    // region: Component Tree
    var id: String? = null
    var parentComponent: Component? = null

    fun setParent(parentComponent: Component) {
        if (this == parentComponent) throw IllegalArgumentException("A Component cannot be its own parent Component")
        this.parentComponent = parentComponent
    }

    fun isAttached(): Boolean = parentComponent != null

    // endregion

    // region: Lifecycle

    var lifecycleState: ComponentLifecycleState = ComponentLifecycleState.Created

    private val _lifecycleStateFlow = MutableStateFlow(lifecycleState)
    val lifecycleStateFlow: StateFlow<ComponentLifecycleState>
        get() = _lifecycleStateFlow.asStateFlow()

    open fun dispatchStart() {
        lifecycleState =
            ComponentLifecycleState.Started // It has to be the first line of this block
        if (deepLinkNavigationAwaitsStartedState) {
            deepLinkNavigationAwaitsStartedState = false
            awaitingDeepLinkMsg?.let {
                DefaultDeepLinkManager().navigateToDeepLink(this, it)
            }
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

    internal var rootBackPressDelegate: (() -> Unit)? = null

    /**
     * If a Component does not override handleBackPressed() function, the default behavior is to
     * delegate/forward the back press event upstream, for its parent Component to handle it.
     * All the way up to the root Component.
     * */
    open fun handleBackPressed() {
        println("${instanceId()}::onBackPressed() handling")
        delegateBackPressedToParent()
    }

    protected open fun delegateBackPressedToParent() {
        val parentComponentCopy = parentComponent
        if (parentComponentCopy != null) {
            println("${instanceId()}::delegateBackPressedToParent()")
            parentComponentCopy.handleBackPressed()
        } else {
            println("${instanceId()}::Back Press reached root component unhandled")
            rootBackPressDelegate?.invoke()
        }
        resetInternal()
    }

    private fun resetInternal() {
        startedFromDeepLink = false
    }

    // endregion

    // region: DeepLink

    internal var deepLinkNavigationAwaitsStartedState = false
    internal var awaitingDeepLinkMsg: DeepLinkMsg? = null
    var startedFromDeepLink = false
    var uriFragment: String? = null

    open fun getChildForNextUriFragment(
        nextUriFragment: String
    ): Component? {
        println("${instanceId()}::getChildForNextUriFragment() has been called but the function is not override in this class")
        return null
    }

    open fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
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
        val addressLast5 = this.toString().let { it.substring(it.length - 5) }
        return "${this::class.simpleName}_${addressLast5}"
    }

    // endregion
}

object ComponentDefaults {
    internal const val EmptyStackMessage =
        "NavigationComponent Empty Stack!.\nYou either did not call setNavItem(...) and/or dispatchStart()"
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
