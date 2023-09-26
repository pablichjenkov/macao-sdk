package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationDemoViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault

class BottomNavigationDemoViewModelFactory(
    private val navBarStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModelFactory<BottomNavigationDemoViewModel> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDemoViewModel>
    ): BottomNavigationDemoViewModel {
        return BottomNavigationDemoViewModel(bottomNavigationComponent, navBarStatePresenter)
    }
}
