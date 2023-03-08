package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.MaterialTheme
import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import example.nodes.AppCoordinatorComponent
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        /*Window("Hello World") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = HelloWorldComponent(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }*/
        BrowserViewportWindow("Hotel Booking") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = AppBuilder.buildGraph(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

