package com.macaosoftware.platform

import com.macaosoftware.component.backpress.DefaultBackPressDispatcher

data class DesktopBridge(
    val onReady: (ComposeReady) -> Unit
)

data class ComposeReady (
    val backPressDispatcher: DefaultBackPressDispatcher
)
