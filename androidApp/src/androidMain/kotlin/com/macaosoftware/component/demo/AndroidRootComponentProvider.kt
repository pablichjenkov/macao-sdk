package com.macaosoftware.component.demo

import androidx.activity.ComponentActivity
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlinx.coroutines.delay

class AndroidRootComponentProvider(
    private val activity: ComponentActivity
) : RootComponentProvider {

    override suspend fun provideRootComponent(
        pluginManager: PluginManager
    ): Component {

        delay(2000)

        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = {
                    activity.finish()
                    true
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        )
    }

}
