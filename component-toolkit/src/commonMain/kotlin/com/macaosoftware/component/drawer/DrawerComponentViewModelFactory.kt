package com.macaosoftware.component.drawer

interface DrawerComponentViewModelFactory<VM : DrawerComponentViewModel> {
    fun create(drawerComponent: DrawerComponent<VM>): VM
}
