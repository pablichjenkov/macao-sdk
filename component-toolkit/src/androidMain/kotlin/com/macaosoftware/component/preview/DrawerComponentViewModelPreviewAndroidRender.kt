package com.macaosoftware.component.preview

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelPreviewAndroidRender(
    drawerComponent: DrawerComponent<DrawerComponentViewModelPreviewAndroidRender>,
    override val drawerStatePresenter: DrawerStatePresenterDefault =
        DrawerComponentDefaults.createDrawerStatePresenter()
) : DrawerComponentViewModel(drawerComponent) {

    override fun onAttach() {
        println("DrawerComponentViewModelPreviewAndroidRender::onAttach()")
    }

    override fun onStart() {
        println("DrawerComponentViewModelPreviewAndroidRender::onStart()")
    }

    override fun onStop() {
        println("DrawerComponentViewModelPreviewAndroidRender::onStop()")
    }

    override fun onDetach() {
        println("DrawerComponentViewModelPreviewAndroidRender::onDetach()")
    }
}
