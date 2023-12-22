package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModelFactory
import com.macaosoftware.component.stack.StackStatePresenter

class StackDemoViewModelFactory(
    private val stackStatePresenter: StackStatePresenter,
    private val onBackPress: () -> Boolean
) : StackComponentViewModelFactory<StackDemoViewModel> {
    override fun create(
        stackComponent: StackComponent<StackDemoViewModel>
    ): StackDemoViewModel {
        return StackDemoViewModel(stackComponent, stackStatePresenter, onBackPress)
    }
}
