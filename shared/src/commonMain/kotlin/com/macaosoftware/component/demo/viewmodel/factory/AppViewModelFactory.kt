package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.AppViewModel
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModel
import com.macaosoftware.component.stack.StackComponentViewModelFactory
import com.macaosoftware.component.stack.StackStatePresenterDefault

class AppViewModelFactory(
    private val stackStatePresenter: StackStatePresenterDefault
) : StackComponentViewModelFactory<AppViewModel> {

    override fun create(
        stackComponent: StackComponent<AppViewModel>
    ): StackComponentViewModel {
        return AppViewModel(
            stackComponent = stackComponent,
            stackStatePresenter = stackStatePresenter
        )
    }
}
