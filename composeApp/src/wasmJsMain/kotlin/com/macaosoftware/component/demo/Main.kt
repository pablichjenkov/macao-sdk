package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.plugin.app.MacaoApplication
import com.macaosoftware.plugin.app.MacaoApplicationState

fun main() {
    val applicationState = MacaoApplicationState(
        rootComponentProvider = BrowserRootComponentProvider(),
        pluginInitializer = BrowserPluginInitializer()
    )

    CanvasBasedWindow(
        title = "Macao SDK Wasm Demo",
        canvasElementId = "ComposeTarget"
    ) {
        MacaoApplication(applicationState = applicationState)
    }
}
