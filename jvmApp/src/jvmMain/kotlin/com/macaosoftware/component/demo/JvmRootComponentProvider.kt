package com.macaosoftware.component.demo

import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

class JvmRootComponentProvider : RootComponentProvider {
    override suspend fun provideRootComponent(pluginManager: PluginManager): Component {

        delay(2000)

        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = {
                    exitProcess(0)
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.uriFragment = "_root_navigator_stack"
        }
    }
}
