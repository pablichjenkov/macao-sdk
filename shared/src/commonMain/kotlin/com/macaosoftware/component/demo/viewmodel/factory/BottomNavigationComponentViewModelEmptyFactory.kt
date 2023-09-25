package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationComponentViewModelEmpty
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault

class BottomNavigationComponentViewModelEmptyFactory : BottomNavigationComponentViewModelFactory<NavBarStatePresenterDefault> {
    override fun create(
        navBarComponent: NavBarComponent<NavBarStatePresenterDefault>
    ): BottomNavigationComponentViewModel<NavBarStatePresenterDefault> {
        return BottomNavigationComponentViewModelEmpty(navBarComponent)
    }
}