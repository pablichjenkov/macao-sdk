package com.macaosoftware.component.demo

import com.macaosoftware.app.RootComponentKoinProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import platform.posix.exit

class IosRootComponentKoinProvider : RootComponentKoinProvider {

    override suspend fun provideRootComponent(
        koinComponent: KoinComponent
    ): Component {

        delay(2000)

        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = {
                    exit(0)
                    true
                }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.uriFragment = "_root_navigator_stack"
        }
    }
}