package com.macaosoftware.plugin

interface PluginManager {
    fun lifecycleDispatcher(): AppLifecycleDispatcher
    fun backPressDispatcher(): BackPressDispatcher
    fun coroutineDispatchers(): CoroutineDispatchers
}