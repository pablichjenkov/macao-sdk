package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.view.DemoType
import com.macaosoftware.component.demo.viewmodel.MainScreenViewModel
import com.macaosoftware.component.viewmodel.ComponentViewModelFactory
import com.macaosoftware.component.viewmodel.StateComponent

class MainScreenViewModelFactory(
    private val onItemSelected: (DemoType) -> Unit,
    private val onBackPress: () -> Boolean
) :ComponentViewModelFactory<MainScreenViewModel> {
    override fun create(
        component: StateComponent<MainScreenViewModel>
    ): MainScreenViewModel {
        return MainScreenViewModel(onItemSelected, onBackPress)
    }
}