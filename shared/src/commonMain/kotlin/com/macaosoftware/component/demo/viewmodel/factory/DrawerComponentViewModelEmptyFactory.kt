package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.DrawerComponentViewModelEmpty
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory

class DrawerComponentViewModelEmptyFactory : DrawerComponentViewModelFactory<DrawerComponentViewModelEmpty> {
    override fun create(
        drawerComponent: DrawerComponent<DrawerComponentViewModelEmpty>
    ): DrawerComponentViewModelEmpty {
        return DrawerComponentViewModelEmpty(drawerComponent)
    }
}
