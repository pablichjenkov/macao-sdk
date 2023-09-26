package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationComponentViewModelEmpty
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault

class BottomNavigationComponentViewModelEmptyFactory : BottomNavigationComponentViewModelFactory<BottomNavigationStatePresenterDefault> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationStatePresenterDefault>
    ): BottomNavigationComponentViewModel<BottomNavigationStatePresenterDefault> {
        return BottomNavigationComponentViewModelEmpty(bottomNavigationComponent)
    }
}