package com.macaosoftware.app

import androidx.compose.runtime.mutableStateOf
import com.macaosoftware.component.core.Component
import com.macaosoftware.plugin.CoroutineDispatchers
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MacaoApplicationState(
    private val pluginManagerInitializer: PluginManagerInitializer,
    private val startupTaskRunner: StartupTaskRunner,
    private val rootComponentInitializer: RootComponentInitializer,
    private val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Default,
) {

    var stage = mutableStateOf<Stage>(Created)
    private val coroutineScope = CoroutineScope(dispatchers.main)

    internal fun initialize() = coroutineScope.launch {

        val result = withContext(dispatchers.default) {
            pluginManagerInitializer.initialize()
        }
        when (result) {
            is MacaoResult.Error -> {
                stage.value = InitializationError(result.error.toString())
            }

            is MacaoResult.Success -> {
                runStartupTasks(result.value)
            }
        }
    }

    private suspend fun runStartupTasks(pluginManager: PluginManager) {

        startupTaskRunner
            .initialize(pluginManager)
            .flowOn(dispatchers.default)
            .collect { status ->
                when (status) {
                    is StartupTaskStatus.Running -> {
                        stage.value = Initializing.StartupTask(status.taskName)
                    }

                    is StartupTaskStatus.CompleteError -> {
                        stage.value = InitializationError(status.errorMsg)
                    }

                    is StartupTaskStatus.CompleteSuccess -> {
                        initializeRootComponent(pluginManager)
                    }
                }
            }
    }

    private suspend fun initializeRootComponent(pluginManager: PluginManager) {

        if (rootComponentInitializer.shouldShowLoader()) {
            stage.value = Initializing.RootComponent
        }
        val result = withContext(dispatchers.default) {
            rootComponentInitializer.initialize(pluginManager)
        }

        when(result) {
            is MacaoResult.Error -> {
                stage.value = InitializationError(result.error.toString())
            }
            is MacaoResult.Success -> {
                stage.value = InitializationSuccess(result.value)
            }
        }
    }
}

sealed class Stage
data object Created : Stage()

sealed class Initializing : Stage() {
    data object PluginManager : Initializing()
    data class StartupTask(val taskName: String) : Initializing()
    data object RootComponent : Initializing()
}

class InitializationError(val errorMsg: String) : Stage()
class InitializationSuccess(val rootComponent: Component) : Stage()
