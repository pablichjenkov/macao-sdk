package com.macaosoftware.plugin.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import com.macaosoftware.plugin.CoroutineDispatchers
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MacaoApplicationState(
    private val rootComponentProvider: RootComponentProvider,
    private val pluginInitializer: PluginInitializer,
    private val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Default,
) {

    var stage = mutableStateOf<Stage>(Created)
    private val coroutineScope = CoroutineScope(dispatchers.main)

    internal fun initialize() = coroutineScope.launch {

        val shouldShowLoader = pluginInitializer.shouldShowLoader()

        if (shouldShowLoader) {
            stage.value = Initializing.PluginManager
        }
        val result = withContext(dispatchers.default) {
            pluginInitializer.initialize()
        }
        when (result) {
            is MacaoResult.Error -> {
                stage.value = InitializationError(result.error.toString())
                return@launch
            }

            is MacaoResult.Success -> {
                initializeRootComponent(result.value)
            }
        }
    }

    private suspend fun initializeRootComponent(pluginManager: PluginManager) {

        // If the Root Component is defined remotely we should fetch it while showing a Splash animation
        stage.value = Initializing.RootComponent
        val rootComponent = withContext(dispatchers.default) {
            // TODO: Remove this, only for simulating a network request
            delay(2000)
            rootComponentProvider.provideRootComponent(pluginManager)
        }

        stage.value = InitializationSuccess(rootComponent)
    }
}

sealed class Stage

data object Created : Stage()

sealed class Initializing : Stage() {
    data object PluginManager : Initializing()
    data object RootComponent : Initializing()
}

class InitializationError(val errorMsg: String) : Stage()
class InitializationSuccess(val rootComponent: Component) : Stage()
