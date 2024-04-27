package com.macaosoftware.component.demo

import androidx.compose.ui.window.CanvasBasedWindow
import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask
import com.macaosoftware.plugin.app.MacaoApplication
import com.macaosoftware.plugin.app.MacaoApplicationState
import com.macaosoftware.plugin.app.StartupTaskRunnerDefault

fun main() {

    val startupTasks = listOf(
        DatabaseMigrationStartupTask(),
        LaunchDarklyStartupTask(),
        SdkXyzStartupTask()
    )

    val applicationState = MacaoApplicationState(
        pluginManagerInitializer = BrowserPluginManagerInitializer(),
        startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
        rootComponentInitializer = BrowserRootComponentInitializer()
    )

    CanvasBasedWindow(
        title = "Macao SDK Wasm Demo",
        canvasElementId = "ComposeTarget"
    ) {
        MacaoApplication(applicationState = applicationState)
    }
}
