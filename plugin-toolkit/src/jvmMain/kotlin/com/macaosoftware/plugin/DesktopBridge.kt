package com.macaosoftware.plugin

import com.macaosoftware.plugin.BackPressDispatcher
import com.macaosoftware.plugin.DefaultBackPressDispatcher

data class DesktopBridge(
    val backPressDispatcher: BackPressDispatcher = DefaultBackPressDispatcher(),
)
