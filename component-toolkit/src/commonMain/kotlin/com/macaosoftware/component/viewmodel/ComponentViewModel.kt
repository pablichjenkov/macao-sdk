package com.macaosoftware.component.viewmodel

import com.macaosoftware.component.core.ComponentLifecycleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ComponentViewModel : ComponentViewModelLifecycle() {
    protected var lifecycleState: ComponentLifecycleState = ComponentLifecycleState.Attached

    private val _lifecycleStateFlow = MutableStateFlow(lifecycleState)
    val lifecycleStateFlow: StateFlow<ComponentLifecycleState>
        get() = _lifecycleStateFlow.asStateFlow()

    open fun dispatchAttached() {
        lifecycleState = ComponentLifecycleState.Attached
        onAttach()
        _lifecycleStateFlow.value = ComponentLifecycleState.Attached
    }

    open fun dispatchStart() {
        // It has to be the first line of this block
        lifecycleState = ComponentLifecycleState.Started
        onStart()
        _lifecycleStateFlow.value = ComponentLifecycleState.Started
    }

    open fun dispatchStop() {
        lifecycleState = ComponentLifecycleState.Stopped
        onStop()
        _lifecycleStateFlow.value = ComponentLifecycleState.Stopped
    }

    open fun dispatchDetach() {
        lifecycleState = ComponentLifecycleState.Detached
        onDetach()
        _lifecycleStateFlow.value = ComponentLifecycleState.Detached
    }

    /**
     * Returns true if the back press event was consumed false otherwise
     * */
    open fun handleBackPressed(): Boolean {
        return false
    }

}
