package com.macaosoftware.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.MacaoComposeUIViewControllerDelegate
import com.macaosoftware.plugin.IosBridge
import platform.UIKit.UIViewController

fun MacaoKoinComposeViewController(
    iosBridge: IosBridge,
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit = {},
    screenColorWhileKoinLoads: Color? = null
): UIViewController {

    return ComposeUIViewController(
        configure = {
            delegate = MacaoComposeUIViewControllerDelegate(iosBridge.platformLifecyclePlugin)
        }
    ) {
        MacaoKoinApplication(
            iosBridge = iosBridge,
            applicationState = applicationState,
            onBackPress = onBackPress,
            screenColorWhileKoinLoads = screenColorWhileKoinLoads
        )
    }
}
