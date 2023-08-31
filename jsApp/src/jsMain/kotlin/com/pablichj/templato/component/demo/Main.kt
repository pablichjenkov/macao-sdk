package com.pablichj.templato.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerComponentDefaults
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelComponentDefaults
import com.pablichj.templato.component.demo.componentDelegates.DrawerComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.PanelComponentDelegate1
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.platform.JsBridge
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        val adaptiveSizeComponent = AdaptableSizeTreeBuilder.build().also {
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

        // val panelComponent = PanelTreeBuilder.build()

        CanvasBasedWindow("Component Demo") {
            BrowserComponentRender(
                rootComponent = adaptiveSizeComponent,
                jsBridge = JsBridge(),
                onBackPress = {
                    println("Back press dispatched in root component")
                }
            )
        }

    }
}
