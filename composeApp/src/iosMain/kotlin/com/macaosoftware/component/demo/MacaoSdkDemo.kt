package com.macaosoftware.component.demo

import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.StartupTaskRunnerDefault
import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.DemoRootComponentInitializer
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask
import platform.UIKit.UIViewController

fun buildDemoViewController(
    iosBridge: IosBridge
): UIViewController = ComposeUIViewController {

    val startupTasks = listOf(
        DatabaseMigrationStartupTask(),
        LaunchDarklyStartupTask(),
        SdkXyzStartupTask()
    )

    val applicationState = MacaoApplicationState(
        pluginManagerInitializer = IosPluginManagerInitializer(iosBridge),
        startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
        rootComponentInitializer = DemoRootComponentInitializer()
    )

    MacaoApplication(applicationState = applicationState)
}

fun createPlatformBridge(
    // firebaseAuthKmpWrapper: FirebaseAuthKmpWrapper
): IosBridge {

    //val firebaseAccountPlugin = FirebaseAccountPlugin(firebaseAuthKmpWrapper)

    return IosBridge(
        // accountPlugin = firebaseAccountPlugin
    )
}
