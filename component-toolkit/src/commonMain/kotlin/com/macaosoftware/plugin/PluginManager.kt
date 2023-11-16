package com.macaosoftware.plugin

interface PluginManager {
    fun lifecycleDispatcher(): PlatformLifecyclePlugin
    fun backPressDispatcher(): BackPressDispatcherPlugin
    fun coroutineDispatchers(): CoroutineDispatchers
}