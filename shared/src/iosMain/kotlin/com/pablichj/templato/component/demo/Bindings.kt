package com.pablichj.templato.component.demo

import com.pablichj.templato.component.platform.IosBridge
import com.pablichj.templato.component.platform.IOSBridge2
import com.pablichj.templato.component.core.IosComponentRender
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.demo.treebuilders.DrawerTreeBuilder
import com.pablichj.templato.component.demo.treebuilders.FullAppWithIntroTreeBuilder
import com.pablichj.templato.component.demo.treebuilders.PagerTreeBuilder
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.pablichj.templato.component.demo.componentDelegates.DrawerComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.PanelComponentDelegate1
import com.macaosoftware.platform.DiContainer
import com.macaosoftware.platform.CoroutineDispatchers
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
                componentDelegate = DrawerComponentDelegate1(),
                content = DrawerComponentDefaults.DrawerComponentView
            )
        )
        //it.setCompactContainer(PagerComponent())
        it.setMediumContainer(
            NavBarComponent(
                navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                componentDelegate = NavBarComponentDelegate1(),
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
