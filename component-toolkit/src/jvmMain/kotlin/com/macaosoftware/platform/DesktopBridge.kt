package com.macaosoftware.platform

import com.macaosoftware.component.backpress.BackPressDispatcher
import com.macaosoftware.component.backpress.DefaultBackPressDispatcher

data class DesktopBridge(
    val backPressDispatcher: BackPressDispatcher = DefaultBackPressDispatcher(),
)
