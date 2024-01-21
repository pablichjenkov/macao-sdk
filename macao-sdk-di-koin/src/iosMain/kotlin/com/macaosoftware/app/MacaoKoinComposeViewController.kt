package com.macaosoftware.app

import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.MacaoComposeUIViewControllerDelegate
import com.macaosoftware.plugin.IosBridge
import platform.UIKit.UIViewController

fun MacaoKoinComposeViewController(
    iosBridge: IosBridge,
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit = {}
): UIViewController {

    return ComposeUIViewController(
        configure = {
            delegate = MacaoComposeUIViewControllerDelegate(iosBridge.platformLifecyclePlugin)
        }
    ) {
        MacaoKoinApplication(
            iosBridge = iosBridge,
            applicationState = applicationState,
            onBackPress = onBackPress
        )
    }
}
