package com.macaosoftware.component.navbar

interface BottomNavigationComponentViewModelFactory<T : NavBarStatePresenter> {
    fun create(navBarComponent: NavBarComponent<T>): BottomNavigationComponentViewModel<T>
}
