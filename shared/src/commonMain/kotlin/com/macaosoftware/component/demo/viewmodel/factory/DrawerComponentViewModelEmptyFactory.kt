package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.DrawerComponentViewModelEmpty
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelEmptyFactory: DrawerComponentViewModelFactory<DrawerStatePresenterDefault> {
    override fun create(
        drawerComponent: DrawerComponent<DrawerStatePresenterDefault>
    ): DrawerComponentViewModel<DrawerStatePresenterDefault> {
        return DrawerComponentViewModelEmpty(drawerComponent)
    }
}
