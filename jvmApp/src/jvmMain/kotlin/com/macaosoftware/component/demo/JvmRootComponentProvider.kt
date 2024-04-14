package com.macaosoftware.component.demo

import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlin.system.exitProcess

class JvmRootComponentProvider : RootComponentProvider {
    override suspend fun provideRootComponent(pluginManager: PluginManager): Component {

        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = {
                    exitProcess(0)
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }
    }
}
