package com.macaosoftware.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentDelegate
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponentDelegate
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelComponentDelegate
import com.macaosoftware.platform.JsBridge
import com.macaosoftware.component.demo.treebuilders.AdaptableSizeTreeBuilder
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
                    componentDelegate = DrawerComponentDelegate(),
                    content = DrawerComponentDefaults.DrawerComponentView
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                    componentDelegate = NavBarComponentDelegate(),
                    content = NavBarComponentDefaults.NavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
                    componentDelegate = PanelComponentDelegate(),
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
