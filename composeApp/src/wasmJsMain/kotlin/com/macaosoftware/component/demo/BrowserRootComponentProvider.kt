package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.PluginManager
import com.macaosoftware.plugin.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModel
import com.macaosoftware.component.demo.startup.StartupCoordinatorViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults

class BrowserRootComponentProvider : RootComponentProvider {

    override suspend fun provideRootComponent(
        pluginManager: PluginManager
    ): Component {

        return StackComponent<StartupCoordinatorViewModel>(
            viewModelFactory = StartupCoordinatorViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                pluginManager = pluginManager,
                onBackPress = { true }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }
    }
}