package com.macaosoftware.component.navbar

interface BottomNavigationComponentViewModelFactory<T : NavBarStatePresenter> {
    fun create(navBarComponent: NavBarComponent<T>): BottomNavigationComponentViewModel<T>
}

class DrawerComponentViewModelFactoryDefault : BottomNavigationComponentViewModelFactory<NavBarStatePresenterDefault> {
    override fun create(
        navBarComponent: NavBarComponent<NavBarStatePresenterDefault>
    ): BottomNavigationComponentViewModel<NavBarStatePresenterDefault> {
        return BottomNavigationComponentDefaultViewModel(navBarComponent)
    }
}
