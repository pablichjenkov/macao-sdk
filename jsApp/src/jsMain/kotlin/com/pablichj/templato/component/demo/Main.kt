package com.pablichj.templato.component.demo

import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.BrowserViewportWindow
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.demo.treebuilders.PanelTreeBuilder
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
                    config = DrawerComponent.DefaultConfig,
                    diContainer = diContainer
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent())
        }

        // val panelComponent = PanelTreeBuilder.build()

        /*Window("Component Demo") {
            BrowserComponentRender(
                rootComponent = panelComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }*/
        BrowserViewportWindow ("Component Demo") {
            BrowserComponentRender(
                rootComponent = adaptiveSizeComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}
