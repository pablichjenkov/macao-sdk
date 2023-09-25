package com.macaosoftware.component.preview

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelPreviewAndroidRender(
    drawerComponent: DrawerComponent<DrawerStatePresenterDefault>,
    override val drawerStatePresenter: DrawerStatePresenterDefault =
        DrawerComponentDefaults.createDrawerStatePresenter()
) : DrawerComponentViewModel<DrawerStatePresenterDefault>(drawerComponent) {

    override fun onCreate() {
        println("DrawerComponentViewModelPreviewAndroidRender::onCreate()")
    }

    override fun onStart() {
        println("DrawerComponentViewModelPreviewAndroidRender::onStart()")
    }

    override fun onStop() {
        println("DrawerComponentViewModelPreviewAndroidRender::onStop()")
    }

    override fun onDestroy() {
        println("DrawerComponentViewModelPreviewAndroidRender::onDestroy()")
    }
}
