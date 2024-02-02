package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoKoinApplication
import com.macaosoftware.app.MacaoKoinApplicationState
import com.macaosoftware.component.demo.plugin.DemoKoinRootModuleInitializer
import kotlinx.coroutines.Dispatchers
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val applicationState = MacaoKoinApplicationState(
            dispatcher = Dispatchers.Default,
            rootComponentKoinProvider = BrowserRootComponentKoinProvider(),
            koinRootModuleInitializer = DemoKoinRootModuleInitializer()
        )

        CanvasBasedWindow("Macao SDK Demo") {
            MacaoKoinApplication(
                onBackPress = {
                    println("Back press dispatched in root node")
                },
                applicationState = applicationState
            )
        }
    }
}
