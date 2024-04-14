package com.macaosoftware.component.demo.viewmodel.root

import com.macaosoftware.component.demo.view.DemoType
import com.macaosoftware.component.demo.viewmodel.root.RootScreenViewModel
import com.macaosoftware.component.viewmodel.ComponentViewModelFactory
import com.macaosoftware.component.viewmodel.StateComponent

class RootScreenViewModelFactory(
    private val onItemSelected: (DemoType) -> Unit,
    private val onBackPress: () -> Boolean
) :ComponentViewModelFactory<RootScreenViewModel> {
    override fun create(
        component: StateComponent<RootScreenViewModel>
    ): RootScreenViewModel {
        return RootScreenViewModel(onItemSelected, onBackPress)
    }
}