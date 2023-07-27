package com.pablichj.templato.component.demo

import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.BrowserViewportWindow
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        val adaptiveSizeComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent(
                    drawerStatePresenter = DrawerComponent.createDefaultState(),
                    config = DrawerComponent.DefaultConfig,
                    diContainer = diContainer,
                    content = DrawerComponent.DefaultDrawerComponentView
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponent.createDefaultState(),
                    config = NavBarComponent.DefaultConfig,
                    content = NavBarComponent.DefaultNavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponent.createDefaultState(),
                    config = PanelComponent.DefaultConfig,
                    content = PanelComponent.DefaultPanelComponentView
                )
            )
        }

        // val panelComponent = PanelTreeBuilder.build()

        /*Window("Component Demo") {
            BrowserComponentRender(
                rootComponent = panelComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root component")
                }
            )
        }*/
        BrowserViewportWindow ("Component Demo") {
            BrowserComponentRender(
                rootComponent = adaptiveSizeComponent,
                onBackPress = {
                    println("Back press dispatched in root component")
                }
            )
        }
    }
}
