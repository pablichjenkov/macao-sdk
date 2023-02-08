package com.pablichj.incubator.uistate3.demo

import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val PanelNode = PanelTreeBuilder.build()

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
                rootComponent = PanelNode,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}

