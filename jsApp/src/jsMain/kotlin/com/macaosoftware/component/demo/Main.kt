package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.component.demo.view.SplashScreen
import com.macaosoftware.plugin.JsBridge
import kotlinx.coroutines.Dispatchers
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val macaoApplicationState = MacaoApplicationState(
            dispatcher = Dispatchers.Default,
            rootComponentProvider = BrowserRootComponentProvider(),
            pluginInitializer = DemoPluginInitializer()
        )

        CanvasBasedWindow("Macao SDK Demo") {
            MacaoApplication(
                jsBridge = JsBridge(),
                onBackPress = {
                    println("Back press dispatched in root node")
                },
                macaoApplicationState = macaoApplicationState,
                splashScreenContent = { SplashScreen() }
            )
        }
    }
}
