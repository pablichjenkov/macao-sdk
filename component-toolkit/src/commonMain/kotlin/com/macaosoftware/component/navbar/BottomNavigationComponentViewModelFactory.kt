package com.macaosoftware.component.navbar

interface BottomNavigationComponentViewModelFactory<T : BottomNavigationStatePresenter> {
    fun create(bottomNavigationComponent: BottomNavigationComponent<T>): BottomNavigationComponentViewModel<T>
}
