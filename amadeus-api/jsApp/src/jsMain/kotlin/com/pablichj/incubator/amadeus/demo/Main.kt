package com.pablichj.incubator.amadeus.demo

import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        val hotelDemoComponent = HotelDemoComponent()
        BrowserViewportWindow("Amadeus API Demo") {
            BrowserComponentRender(
                rootComponent = hotelDemoComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root node")
                }
            )
        }
    }
}

