package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationDemoViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault

class BottomNavigationDemoViewModelFactory(
    private val navBarStatePresenter: NavBarStatePresenterDefault
) : BottomNavigationComponentViewModelFactory<NavBarStatePresenterDefault> {
    override fun create(
        navBarComponent: NavBarComponent<NavBarStatePresenterDefault>
    ): BottomNavigationComponentViewModel<NavBarStatePresenterDefault> {
        return BottomNavigationDemoViewModel(navBarComponent, navBarStatePresenter)
    }
}
