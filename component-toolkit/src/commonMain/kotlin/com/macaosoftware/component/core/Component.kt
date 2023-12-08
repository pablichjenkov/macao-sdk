package com.macaosoftware.component.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Component : ComponentLifecycle() {

    // region: Component Tree

    var id: String? = null
    var parentComponent: Component? = null
        private set

    fun setParent(parentComponent: Component) {
        if (this == parentComponent) throw IllegalArgumentException("A Component cannot be its own parent Component")
        // Protect against double calling
        if (this.parentComponent == parentComponent) {
            return
        }
        this.parentComponent = parentComponent
        dispatchAttach()
    }

    fun isAttached(): Boolean = lifecycleState == ComponentLifecycleState.Attached

    // endregion

    // region: Lifecycle

    var lifecycleState: ComponentLifecycleState = ComponentLifecycleState.Attached

    private val _lifecycleStateFlow = MutableStateFlow(lifecycleState)
    val lifecycleStateFlow: StateFlow<ComponentLifecycleState>
        get() = _lifecycleStateFlow.asStateFlow()

    internal fun dispatchAttach() {
        lifecycleState = ComponentLifecycleState.Attached
        onAttach()
        _lifecycleStateFlow.value = ComponentLifecycleState.Attached
    }

    open fun dispatchStart() {
        // Protect against double calling
        if (lifecycleState == ComponentLifecycleState.Started) {
            return
        }
        // It has to be the first line of this block
        lifecycleState = ComponentLifecycleState.Started
        if (deepLinkNavigationAwaitsStartedState) {
            deepLinkNavigationAwaitsStartedState = false
            awaitingDeepLinkMsg?.let {
                DefaultDeepLinkManager().navigateToDeepLink(this, it)
            }
            return
        }
        onStart()
        _lifecycleStateFlow.value = ComponentLifecycleState.Started
    }

    open fun dispatchStop() {
        // Protect against double calling
        if (lifecycleState == ComponentLifecycleState.Stopped) {
            return
        }
        lifecycleState = ComponentLifecycleState.Stopped
        onStop()
        _lifecycleStateFlow.value = ComponentLifecycleState.Stopped
        resetStartedValuesInternal()
    }

    open fun dispatchDetach() {
        // Protect against double calling
        if (lifecycleState == ComponentLifecycleState.Detached) {
            return
        }
        lifecycleState = ComponentLifecycleState.Detached
        onDetach()
        _lifecycleStateFlow.value = ComponentLifecycleState.Detached
        this.parentComponent = null
    }

    override fun onAttach() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDetach() {
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
        println("${instanceId()}::handleBackPressed() Base Component")
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
    }

    private fun resetStartedValuesInternal() {
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
    object Attached : ComponentLifecycleState
    object Started : ComponentLifecycleState
    object Stopped : ComponentLifecycleState
    object Detached : ComponentLifecycleState
}
