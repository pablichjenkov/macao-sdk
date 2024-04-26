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

            withContext(dispatchers.default) {
                pluginInitializer.initialize(pluginManager)
            }

            // If the Root Component is defined remotely we should fetch it while showing a Splash animation
            // stage.value = Stage.InitializingDiAndRootComponent("Initializing RootComponent")

            val rootComponent = withContext(dispatchers.default) {
                rootComponentProvider.provideRootComponent(pluginManager)
            }

            stage.value = Stage.Started(rootComponent)
        }
    }
}

sealed class Stage {
    data object Created : Stage()
    data class InitializingDiAndRootComponent(val initializerName: String) : Stage()
    class Started(val rootComponent: Component) : Stage()
}
