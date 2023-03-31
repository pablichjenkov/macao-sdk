package com.pablichj.incubator.amadeus.demo

import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        val amadeusDemoComponent = AmadeusDemoComponent()
        BrowserViewportWindow("Amadeus API Demo") {
            BrowserComponentRender(
                rootComponent = amadeusDemoComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}

