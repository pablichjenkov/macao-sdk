package com.macaosoftware.component.demo

import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.componentDelegates.PanelComponentDelegate1
import com.macaosoftware.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.macaosoftware.component.demo.treebuilders.DrawerTreeBuilder
import com.macaosoftware.component.demo.treebuilders.FullAppWithIntroTreeBuilder
import com.macaosoftware.component.demo.treebuilders.PagerTreeBuilder
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.platform.CoroutineDispatchers
import com.macaosoftware.platform.DiContainer
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
    return DrawerTreeBuilder.build()
}

fun buildPagerComponent(): Component {
    return PagerTreeBuilder.build()
}

fun buildAdaptableSizeComponent(): Component {
    val diContainer = DiContainer(CoroutineDispatchers.Defaults)
    val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
    return AdaptableSizeTreeBuilder.build().also {
        it.setNavItems(subtreeNavItems, 0)
        it.setCompactContainer(
            DrawerComponent(
                drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
                componentViewModel = DrawerComponentDefaults.createViewModel(),
                content = DrawerComponentDefaults.DrawerComponentView
            )
        )
        //it.setCompactContainer(PagerComponent())
        it.setMediumContainer(
            NavBarComponent(
                navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                componentViewModel = NavBarComponentDefaults.createViewModel(),
                content = NavBarComponentDefaults.NavBarComponentView
            )
        )
        it.setExpandedContainer(
            PanelComponent(
                panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
                componentDelegate = PanelComponentDelegate1(),
                content = PanelComponentDefaults.PanelComponentView
            )
        )
    }
}

fun buildAppWithIntroComponent(): Component {
    return FullAppWithIntroTreeBuilder.build()
}
