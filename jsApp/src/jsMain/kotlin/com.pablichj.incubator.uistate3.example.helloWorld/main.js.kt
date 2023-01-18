package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("Hello World") {
            Column(modifier = Modifier.fillMaxSize()) {
                HelloWorldApp()
            }
        }
    }
}

