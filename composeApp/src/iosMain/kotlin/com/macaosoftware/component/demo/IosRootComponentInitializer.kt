package com.macaosoftware.component.demo

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModel
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.plugin.app.InitializerWithInput
import com.macaosoftware.plugin.app.PluginManager
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.delay
import platform.posix.exit

class IosRootComponentInitializer : InitializerWithInput<PluginManager, Component> {

    override fun shouldShowLoader(): Boolean {
        return true
    }

    override suspend fun initialize(pluginManager: PluginManager): MacaoResult<Component> {
        // TODO: Remove this, only for simulating a network request
        delay(1000)

        // val httpClient = pluginManager.ktorClient
        //

        val rootComponent = StackComponent<StartupCoordinatorViewModel>(
            viewModelFactory = StartupCoordinatorViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                pluginManager = pluginManager,
                onBackPress = {
                    exit(0)
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
