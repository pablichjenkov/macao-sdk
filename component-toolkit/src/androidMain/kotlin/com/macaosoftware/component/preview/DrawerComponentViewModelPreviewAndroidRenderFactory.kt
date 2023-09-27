package com.macaosoftware.component.preview

import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory

class DrawerComponentViewModelPreviewAndroidRenderFactory : DrawerComponentViewModelFactory<DrawerComponentViewModelPreviewAndroidRender> {

    override fun create(
        drawerComponent: DrawerComponent<DrawerComponentViewModelPreviewAndroidRender>
    ): DrawerComponentViewModelPreviewAndroidRender {
        return DrawerComponentViewModelPreviewAndroidRender(drawerComponent)
    }
}