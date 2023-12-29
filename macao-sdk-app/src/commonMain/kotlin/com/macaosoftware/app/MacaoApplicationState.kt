package com.macaosoftware.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MacaoApplicationState(
    dispatcher: CoroutineDispatcher,
    val rootComponentProvider: RootComponentProvider,
    pluginInitializer: PluginInitializer
) {
    val coroutineScope = CoroutineScope(dispatcher)

    var rootComponentState = mutableStateOf<Component?>(null)

    val pluginManager = PluginManager()

    init {
        pluginInitializer.initialize(pluginManager)
    }

    fun fetchRootComponent() {
        coroutineScope.launch {
            rootComponentState.value = rootComponentProvider.provideRootComponent(pluginManager)
        }
    }
}
