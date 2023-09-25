package com.macaosoftware.component.preview

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class DrawerComponentViewModelPreviewAndroidRenderFactory : DrawerComponentViewModelFactory<DrawerStatePresenterDefault> {

    override fun create(drawerComponent: DrawerComponent<DrawerStatePresenterDefault>): DrawerComponentViewModel<DrawerStatePresenterDefault> {
        return DrawerComponentViewModelPreviewAndroidRender(drawerComponent)
    }
}