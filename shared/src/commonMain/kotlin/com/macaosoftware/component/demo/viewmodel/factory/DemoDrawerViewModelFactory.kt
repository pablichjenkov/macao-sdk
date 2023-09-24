package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.DrawerDemoViewModel
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerDemoViewModelFactory(
    private val drawerStatePresenter: DrawerStatePresenterDefault
) : DrawerComponentViewModelFactory<DrawerStatePresenterDefault> {
    override fun create(drawerComponent: DrawerComponent<DrawerStatePresenterDefault>): DrawerComponentViewModel<DrawerStatePresenterDefault> {
        return DrawerDemoViewModel(drawerComponent, drawerStatePresenter)
    }
}
