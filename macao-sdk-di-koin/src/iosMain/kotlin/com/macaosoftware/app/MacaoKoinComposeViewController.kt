package com.macaosoftware.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MacaoKoinComposeViewController(
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {
    MacaoKoinApplication(
        applicationState = applicationState,
        onBackPress = onBackPress
    )
}
