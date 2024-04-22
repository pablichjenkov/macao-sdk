package com.macaosoftware.component.demo

import androidx.activity.ComponentActivity
import com.macaosoftware.plugin.app.PluginManager
import com.macaosoftware.plugin.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults

class AndroidRootComponentProvider(
    private val activity: ComponentActivity
) : RootComponentProvider {

    override suspend fun provideRootComponent(
        pluginManager: PluginManager
    ): Component {

        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = {
                    activity.finish()
                    true
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }
    }

}
