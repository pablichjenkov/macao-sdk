package com.pablichj.templato.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.platform.CoroutineDispatchers
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        val adaptiveSizeComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent(
                    drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
                    config = DrawerComponent.DefaultConfig,
                    dispatchers = CoroutineDispatchers.Defaults,
                    content = DrawerComponent.DefaultDrawerComponentView
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
                    config = NavBarComponent.DefaultConfig,
                    content = NavBarComponent.DefaultNavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponent.createDefaultPanelStatePresenter(),
                    config = PanelComponent.DefaultConfig,
                    content = PanelComponent.DefaultPanelComponentView
                )
            )
        }

        // val panelComponent = PanelTreeBuilder.build()

        CanvasBasedWindow("Component Demo") {
            BrowserComponentRender(
                rootComponent = adaptiveSizeComponent,
                onBackPress = {
                    println("Back press dispatched in root component")
                }
            )
        }

    }
}
