package com.macaosoftware.component.core

abstract class ComponentLifecycle {
    protected abstract fun onAttach()
    protected abstract fun onActive()
    protected abstract fun onInactive()
    protected abstract fun onDetach()
}

sealed interface ComponentLifecycleState {
    object Attached : ComponentLifecycleState
    object Active : ComponentLifecycleState
    object Inactive : ComponentLifecycleState
    object Detached : ComponentLifecycleState
}

sealed interface PlatformLifecycleState {
    object Created : PlatformLifecycleState
    object Started : PlatformLifecycleState
    object Resumed : PlatformLifecycleState
    object Paused : PlatformLifecycleState
    object Stopped : PlatformLifecycleState
    object Destroyed : PlatformLifecycleState
}
