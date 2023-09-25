package com.macaosoftware.component.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.factory.AdaptiveSizeDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.AppViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.PagerDemoViewModelFactory
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
        viewModelFactory = DrawerDemoViewModelFactory(
            DrawerComponentDefaults.createDrawerStatePresenter()
        ),
        content = DrawerComponentDefaults.DrawerComponentView
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun buildPagerComponent(): Component {
    return PagerComponent(
        viewModelFactory = PagerDemoViewModelFactory(),
        content = PagerComponentDefaults.PagerComponentView
    )
}

fun buildAdaptableSizeComponent(): Component {
    return AdaptiveSizeComponent(AdaptiveSizeDemoViewModelFactory())
}

fun buildAppWithIntroComponent(): Component {
    return AppComponent(
        viewModelFactory = AppViewModelFactory(
            stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
        ),
        content = StackComponentDefaults.DefaultStackComponentView
    )
}
