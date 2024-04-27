package com.macaosoftware.component.demo

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModel
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentInitializer
import com.macaosoftware.util.MacaoResult
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

class JvmRootComponentInitializer : RootComponentInitializer {

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
                    exitProcess(0)
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }

        return MacaoResult.Success(rootComponent)
    }
}
