package com.macaosoftware.component.demo.startup

import com.macaosoftware.app.InitializationError
import com.macaosoftware.plugin.MacaoApplicationCallback
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentInitializer
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.delay

class DemoRootComponentInitializer : RootComponentInitializer {

    override fun shouldShowLoader(): Boolean {
        return true
    }

    override suspend fun initialize(pluginManager: PluginManager): MacaoResult<Component, InitializationError> {

        // TODO: Remove this, only for simulating a network request
        delay(1000)

        // val httpClient = pluginManager.ktorClient
        val macaoApplicationCallback: MacaoApplicationCallback? = pluginManager.getPlugin()

        val rootComponent = StackComponent<StartupCoordinatorViewModel>(
            viewModelFactory = StartupCoordinatorViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                pluginManager = pluginManager,
                onBackPress = {
                    macaoApplicationCallback?.onExit()
                    true
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }

        return MacaoResult.Success(rootComponent)
    }
}
