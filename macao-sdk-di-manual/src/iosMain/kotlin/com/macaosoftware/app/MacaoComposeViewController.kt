package com.macaosoftware.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MacaoComposeViewController(
    applicationState: MacaoApplicationState,
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {
    MacaoApplication(
        applicationState = applicationState,
        onBackPress = onBackPress
    )
}
