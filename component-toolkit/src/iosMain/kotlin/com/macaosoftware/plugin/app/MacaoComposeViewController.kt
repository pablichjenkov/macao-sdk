package com.macaosoftware.plugin.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MacaoComposeViewController(
    applicationState: MacaoApplicationState,
): UIViewController = ComposeUIViewController {
    MacaoApplication(
        applicationState = applicationState
    )
}
