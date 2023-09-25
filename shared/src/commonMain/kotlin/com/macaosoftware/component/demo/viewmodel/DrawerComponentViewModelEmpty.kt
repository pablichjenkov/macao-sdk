package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelEmpty(
    drawerComponent: DrawerComponent<DrawerStatePresenterDefault>,
    override val drawerStatePresenter: DrawerStatePresenterDefault =
        DrawerComponentDefaults.createDrawerStatePresenter()
) : DrawerComponentViewModel<DrawerStatePresenterDefault>(drawerComponent) {

    override fun onCreate() {
        println("DrawerComponentDefaultViewModel::onCreate()")
    }

    override fun onStart() {
        println("DrawerComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("DrawerComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("DrawerComponentDefaultViewModel::onDestroy()")
    }
}
