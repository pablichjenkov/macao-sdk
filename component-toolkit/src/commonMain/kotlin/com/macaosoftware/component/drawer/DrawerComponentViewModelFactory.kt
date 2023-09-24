package com.macaosoftware.component.drawer

interface DrawerComponentViewModelFactory<T : DrawerStatePresenter> {
    fun create(drawerComponent: DrawerComponent<T>): DrawerComponentViewModel<T>
}

class DrawerComponentViewModelFactoryDefault: DrawerComponentViewModelFactory<DrawerStatePresenterDefault> {
    override fun create(
        drawerComponent: DrawerComponent<DrawerStatePresenterDefault>
    ): DrawerComponentViewModel<DrawerStatePresenterDefault> {
        return DrawerComponentDefaultViewModel(drawerComponent)
    }
}
