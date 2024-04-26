package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.MacaoApplicationState
import com.macaosoftware.plugin.app.MacaoApplicationViewController
import platform.UIKit.UIViewController

fun buildDemoViewController(
    iosBridge: IosBridge
): UIViewController {

    val applicationState = MacaoApplicationState(
        rootComponentProvider = IosRootComponentProvider(),
        pluginInitializer = IosPluginInitializer(iosBridge)
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
