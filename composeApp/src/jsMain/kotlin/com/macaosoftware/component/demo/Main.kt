package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.plugin.app.MacaoApplication
import com.macaosoftware.plugin.app.MacaoApplicationState
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val applicationState = MacaoApplicationState(
            rootComponentProvider = BrowserRootComponentProvider(),
            pluginInitializer = DemoPluginInitializer()
        )

        CanvasBasedWindow("Macao SDK Demo") {
            MacaoApplication(applicationState = applicationState)
        }
    }
}
