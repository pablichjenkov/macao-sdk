package com.pablichj.incubator.uistate3.demo

import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import com.pablichj.incubator.uistate3.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.incubator.uistate3.demo.treebuilders.PanelTreeBuilder
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import com.pablichj.incubator.uistate3.platform.DiContainer
import com.pablichj.incubator.uistate3.platform.DispatchersProxy
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

        val panelComponent = PanelTreeBuilder.build()

        /*Window("Component Demo") {
            Column(modifier = Modifier.fillMaxSize()) {
                BrowserComponentRender(
                    rootComponent = panelComponent,
                    onBackPressEvent = {
                        println("Back press dispatched in root node")
                    }
                )
            }
        }*/
        BrowserViewportWindow("Component Demo") {
            BrowserComponentRender(
                rootComponent = adaptiveSizeComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}

