package com.macaosoftware.component.demo.viewmodel.drawer

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelEmptyFactory(
    private val drawerStatePresenter: DrawerStatePresenterDefault
) : DrawerComponentViewModelFactory<DrawerComponentViewModelEmpty> {
    override fun create(
        drawerComponent: DrawerComponent<DrawerComponentViewModelEmpty>
    ): DrawerComponentViewModelEmpty {
        return DrawerComponentViewModelEmpty(
            drawerComponent = drawerComponent,
            drawerStatePresenter = drawerStatePresenter
        )
    }
}
