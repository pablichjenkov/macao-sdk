package com.macaosoftware.component.demo.viewmodel.bottomnavigation

import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault

class BottomNavigationDebugViewModelFactory(
    private val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModelFactory<BottomNavigationDebugViewModel> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDebugViewModel>
    ): BottomNavigationDebugViewModel {
        return BottomNavigationDebugViewModel(bottomNavigationComponent, bottomNavigationStatePresenter)
    }
}