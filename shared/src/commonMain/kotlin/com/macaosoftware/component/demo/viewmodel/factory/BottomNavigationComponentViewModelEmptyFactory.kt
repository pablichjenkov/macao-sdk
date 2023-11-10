package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.BottomNavigationComponentViewModelEmpty
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModelFactory

class BottomNavigationComponentViewModelEmptyFactory : BottomNavigationComponentViewModelFactory<BottomNavigationComponentViewModelEmpty> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationComponentViewModelEmpty>
    ): BottomNavigationComponentViewModelEmpty {
        return BottomNavigationComponentViewModelEmpty(bottomNavigationComponent)
    }
}