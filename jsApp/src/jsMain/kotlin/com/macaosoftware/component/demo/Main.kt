package com.macaosoftware.component.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.component.demo.view.SplashScreen
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.plugin.JsBridge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
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
