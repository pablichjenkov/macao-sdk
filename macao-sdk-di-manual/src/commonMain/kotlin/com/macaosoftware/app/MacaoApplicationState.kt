package com.macaosoftware.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MacaoApplicationState(
    dispatcher: CoroutineDispatcher,
    private val rootComponentProvider: RootComponentProvider,
    private val pluginInitializer: PluginInitializer
) {

    var stage = mutableStateOf<Stage>(Stage.Created)
    private val coroutineScope = CoroutineScope(dispatcher)
    private val pluginManager = PluginManager()

    fun start() {
        coroutineScope.launch {

            stage.value = Stage.Loading
            pluginInitializer.initialize(pluginManager)
            val rootComponent = rootComponentProvider.provideRootComponent(pluginManager)
            stage.value = Stage.Started(rootComponent)
        }
    }
}

sealed class Stage {
    data object Created : Stage()
    data object Loading : Stage()
    class Started(val rootComponent: Component) : Stage()
}
