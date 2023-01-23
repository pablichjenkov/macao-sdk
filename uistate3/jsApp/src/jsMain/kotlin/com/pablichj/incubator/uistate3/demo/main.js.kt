package com.pablichj.incubator.uistate3.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import com.pablichj.incubator.uistate3.BrowserNodeRender
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val PanelNode = PanelTreeBuilder.build()

        Window("State 3 Demo") {
            Column(modifier = Modifier.fillMaxSize()) {
                BrowserNodeRender(
                    rootNode = PanelNode,
                    onBackPressEvent = {
                        println("Back press dispatched in root node")
                    }
                )
            }
        }
    }
}

