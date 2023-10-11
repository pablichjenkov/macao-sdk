package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelEmpty(
    drawerComponent: DrawerComponent<DrawerComponentViewModelEmpty>,
    override val drawerStatePresenter: DrawerStatePresenterDefault =
        DrawerComponentDefaults.createDrawerStatePresenter()
) : DrawerComponentViewModel(drawerComponent) {

    override fun onAttach() {
        println("DrawerComponentDefaultViewModel::onAttach()")
    }

    override fun onStart() {
        println("DrawerComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("DrawerComponentDefaultViewModel::onStop()")
    }

    override fun onDetach() {
        println("DrawerComponentDefaultViewModel::onDetach()")
    }
}
