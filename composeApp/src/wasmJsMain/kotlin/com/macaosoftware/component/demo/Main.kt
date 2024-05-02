package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.StartupTaskRunnerDefault
import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.DemoRootComponentInitializer
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask

fun main() {

    val startupTasks = listOf(
        DatabaseMigrationStartupTask(),
        LaunchDarklyStartupTask(),
        SdkXyzStartupTask()
    )

    val applicationState = MacaoApplicationState(
        pluginManagerInitializer = WasmPluginManagerInitializer(),
        startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
        rootComponentInitializer = DemoRootComponentInitializer()
    )

    CanvasBasedWindow(
        title = "Macao SDK Wasm Demo",
        canvasElementId = "ComposeTarget"
    ) {
        MacaoApplication(applicationState = applicationState)
    }
}
