package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationComponentViewModelEmpty
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModelFactory

class BottomNavigationComponentViewModelEmptyFactory : BottomNavigationComponentViewModelFactory<BottomNavigationComponentViewModelEmpty> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationComponentViewModelEmpty>
    ): BottomNavigationComponentViewModelEmpty {
        return BottomNavigationComponentViewModelEmpty(bottomNavigationComponent)
    }
}