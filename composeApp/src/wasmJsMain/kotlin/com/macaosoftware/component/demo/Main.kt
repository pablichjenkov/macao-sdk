package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.plugin.app.MacaoApplication
import com.macaosoftware.plugin.app.MacaoApplicationState
import kotlinx.coroutines.Dispatchers

fun main() {
    val applicationState = MacaoApplicationState(
        dispatcher = Dispatchers.Default,
        rootComponentProvider = BrowserRootComponentProvider(),
        pluginInitializer = DemoPluginInitializer()
    )

    CanvasBasedWindow(
        title = "Macao SDK Demo",
        canvasElementId = "ComposeTarget"
    ) {
        MacaoApplication(applicationState = applicationState)
    }
}
