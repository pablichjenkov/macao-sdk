package com.macaosoftware.component.demo.viewmodel.bottomnavigation

import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModelFactory

class BottomNavigationComponentViewModelEmptyFactory : BottomNavigationComponentViewModelFactory<BottomNavigationComponentViewModelEmpty> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationComponentViewModelEmpty>
    ): BottomNavigationComponentViewModelEmpty {
        return BottomNavigationComponentViewModelEmpty(bottomNavigationComponent)
    }
}