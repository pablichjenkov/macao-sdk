package com.macaosoftware.component.demo

import com.macaosoftware.app.RootComponentKoinProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.stack.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import org.koin.core.component.KoinComponent

class BrowserRootComponentKoinProvider : RootComponentKoinProvider {

    override suspend fun provideRootComponent(koinComponent: KoinComponent): Component {
        return StackComponent<StackDemoViewModel>(
            viewModelFactory = StackDemoViewModelFactory(
                stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                onBackPress = { true }
            ),
            content = StackComponentDefaults.DefaultStackComponentView
        ).also {
            it.deepLinkPathSegment = "_root_navigator_stack"
        }
    }
}