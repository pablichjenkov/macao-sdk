package com.macaosoftware.component.drawer

interface DrawerComponentViewModelFactory<T : DrawerStatePresenter> {
    fun create(drawerComponent: DrawerComponent<T>): DrawerComponentViewModel<T>
}
