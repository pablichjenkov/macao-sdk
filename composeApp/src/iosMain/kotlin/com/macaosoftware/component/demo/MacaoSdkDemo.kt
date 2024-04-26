package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.MacaoApplicationState
import com.macaosoftware.plugin.app.MacaoComposeViewController
import platform.UIKit.UIViewController

fun buildDemoViewController(
    iosBridge: IosBridge
): UIViewController {

    val applicationState = MacaoApplicationState(
        rootComponentProvider = IosRootComponentProvider(),
        pluginInitializer = IosPluginInitializer(iosBridge)
    )

    return MacaoComposeViewController(
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
