package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.material.MaterialTheme
import com.pablichj.incubator.uistate3.BrowserComponentRender
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import com.pablichj.incubator.uistate3.example.helloWorld.HelloWorldComponent
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
        BrowserViewportWindow("Hello World") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = HelloWorldComponent(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node. Should ste back button invisible")
                    }
                )
            }
        }
    }
}

