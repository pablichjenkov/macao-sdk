package com.macaosoftware.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import com.macaosoftware.plugin.PluginManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MacaoApplicationState(
    dispatcher: CoroutineDispatcher,
    val rootComponentProvider: RootComponentProvider
) {
    val coroutineScope = CoroutineScope(dispatcher)

    var rootComponentState = mutableStateOf<Component?>(null)

    val pluginManager = PluginManager()

    fun fetchRootComponent() {
        coroutineScope.launch {
            rootComponentState.value = rootComponentProvider.provideRootComponent()
        }
    }
}
