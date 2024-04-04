package com.macaosoftware.component.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoKoinApplication
import com.macaosoftware.app.MacaoKoinApplicationState
import com.macaosoftware.component.demo.plugin.DemoKoinRootModuleInitializer
import kotlinx.coroutines.Dispatchers

fun main() {
    val applicationState = MacaoKoinApplicationState(
        dispatcher = Dispatchers.Default,
        rootComponentKoinProvider = BrowserRootComponentKoinProvider(),
        koinRootModuleInitializer = DemoKoinRootModuleInitializer()
    )

    CanvasBasedWindow(
        title = "Macao SDK Demo",
        canvasElementId = "ComposeTarget"
    ) {
        MacaoKoinApplication(applicationState = applicationState)
    }
}
