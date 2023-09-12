package com.macaosoftware.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.demo.componentDelegates.NavBarComponentDelegate1
import com.macaosoftware.component.demo.componentDelegates.PanelComponentDelegate1
import com.macaosoftware.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.platform.JsBridge
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
                    componentViewModel = DrawerComponentDefaults.createViewModel(),
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
