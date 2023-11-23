package com.macaosoftware.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.plugin.JsBridge
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("Component Demo") {
            DemoMainView(
                jsBridge = JsBridge(),
                onBackPress = {
                    println("Back press dispatched in root component")
                }
            )
        }

    }
}
