package com.macaosoftware.plugin.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import com.macaosoftware.plugin.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MacaoApplicationState(
    private val rootComponentProvider: RootComponentProvider,
    private val pluginInitializer: PluginInitializer,
    private val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Default,
) {

    var stage = mutableStateOf<Stage>(Stage.Created)
    private val coroutineScope = CoroutineScope(dispatchers.main)
    private val pluginManager = PluginManager()

    internal fun initialize() {
        coroutineScope.launch {
            stage.value = Stage.InitializingDiAndRootComponent
            val rootComponent = withContext(dispatchers.io) {
                pluginInitializer.initialize(pluginManager)
                rootComponentProvider.provideRootComponent(pluginManager)
            }
            stage.value = Stage.Started(rootComponent)
        }
    }
}

sealed class Stage {
    data object Created : Stage()
    data object InitializingDiAndRootComponent : Stage()
    class Started(val rootComponent: Component) : Stage()
}
