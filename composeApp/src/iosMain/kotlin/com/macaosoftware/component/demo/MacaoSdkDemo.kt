package com.macaosoftware.component.demo

import com.macaosoftware.component.demo.startup.DatabaseMigrationStartupTask
import com.macaosoftware.component.demo.startup.LaunchDarklyStartupTask
import com.macaosoftware.component.demo.startup.SdkXyzStartupTask
import com.macaosoftware.plugin.app.MacaoApplicationState
import com.macaosoftware.plugin.app.MacaoApplicationViewController
import com.macaosoftware.plugin.app.StartupTaskRunnerDefault
import platform.UIKit.UIViewController

fun buildDemoViewController(
    iosBridge: IosBridge
): UIViewController {

    val startupTasks = listOf(
        DatabaseMigrationStartupTask(),
        LaunchDarklyStartupTask(),
        SdkXyzStartupTask()
    )

    val applicationState = MacaoApplicationState(
        pluginManagerInitializer = IosPluginManagerInitializer(iosBridge),
        startupTaskRunner = StartupTaskRunnerDefault(startupTasks),
        rootComponentInitializer = IosRootComponentInitializer()
    )

    return MacaoApplicationViewController(
        applicationState = applicationState
    )
}

fun createPlatformBridge(
    // firebaseAuthKmpWrapper: FirebaseAuthKmpWrapper
): IosBridge {

    //val firebaseAccountPlugin = FirebaseAccountPlugin(firebaseAuthKmpWrapper)

    return IosBridge(
        // accountPlugin = firebaseAccountPlugin
    )
}
