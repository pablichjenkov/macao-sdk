package com.macaosoftware.component.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.AdaptiveSizeDemoViewModel
import com.macaosoftware.component.demo.viewmodel.AppViewModel
import com.macaosoftware.component.demo.viewmodel.DrawerDemoViewModel
import com.macaosoftware.component.demo.viewmodel.PagerDemoViewModel
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentDefaults
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.platform.IOSBridge2
import com.macaosoftware.platform.IosBridge
import platform.Foundation.NSURL
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    iosBridge: IosBridge,
    iosBridge2: IOSBridge2 = IOSBridge2(test = NSURL(string = "kjbkjbk")),
    onBackPress: () -> Unit = {}
): UIViewController = IosComponentRender(rootComponent, iosBridge, onBackPress)

fun buildDrawerComponent(): Component {
    return DrawerComponent(
        componentViewModel = DrawerDemoViewModel(
            DrawerComponentDefaults.createDrawerStatePresenter()
        ),
        content = DrawerComponentDefaults.DrawerComponentView
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun buildPagerComponent(): Component {
    return PagerComponent(
        componentViewModel = PagerDemoViewModel(),
        content = PagerComponentDefaults.PagerComponentView
    )
}

fun buildAdaptableSizeComponent(): Component {
    return AdaptiveSizeComponent(AdaptiveSizeDemoViewModel())
}

fun buildAppWithIntroComponent(): Component {
    return AppComponent(
        stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
        componentViewModel = AppViewModel(),
        content = StackComponentDefaults.DefaultStackComponentView
    )
}
