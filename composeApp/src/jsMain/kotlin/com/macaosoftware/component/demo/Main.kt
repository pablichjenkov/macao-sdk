package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.StartupTaskRunnerDefault
import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.DemoRootComponentInitializer
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {

        val startupTasks = listOf(
            DatabaseMigrationStartupTask(),
            LaunchDarklyStartupTask(),
            SdkXyzStartupTask()
        )

        val applicationState = MacaoApplicationState(
            pluginManagerInitializer = JsPluginManagerInitializer(),
            startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
            rootComponentInitializer = DemoRootComponentInitializer()
        )

        CanvasBasedWindow("Macao SDK JS Demo") {
            MacaoApplication(applicationState = applicationState)
        }
    }
}
