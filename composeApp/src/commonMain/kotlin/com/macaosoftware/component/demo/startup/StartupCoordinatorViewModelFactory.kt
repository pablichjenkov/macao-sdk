package com.macaosoftware.component.demo.startup

import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModelFactory
import com.macaosoftware.component.stack.StackStatePresenter
import com.macaosoftware.app.PluginManager

class StartupCoordinatorViewModelFactory(
    private val stackStatePresenter: StackStatePresenter,
    private val pluginManager: PluginManager,
    private val onBackPress: () -> Boolean
) : StackComponentViewModelFactory<StartupCoordinatorViewModel> {
    override fun create(
        stackComponent: StackComponent<StartupCoordinatorViewModel>
    ): StartupCoordinatorViewModel {
        return StartupCoordinatorViewModel(
            stackComponent,
            stackStatePresenter,
            pluginManager,
            onBackPress
        )
    }
}
