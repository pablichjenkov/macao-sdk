package com.pablichj.incubator.uistate3.demo

import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import com.pablichj.incubator.uistate3.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        val adaptableSizeComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(DrawerComponent())
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent())
        }

        /*Window("State 3 Demo") {
            Column(modifier = Modifier.fillMaxSize()) {
                BrowserNodeRender(
                    rootNode = PanelNode,
                    onBackPressEvent = {
                        println("Back press dispatched in root node")
                    }
                )
            }
        }*/
        BrowserViewportWindow("Hello World") {
            BrowserComponentRender(
                rootComponent = adaptableSizeComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}

