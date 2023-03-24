package com.pablichj.incubator.uistate3

import com.pablichj.incubator.uistate3.platform.DefaultAppLifecycleDispatcher
import com.pablichj.incubator.uistate3.platform.SafeAreaInsets

class DesktopBridge (
    var appLifecycleDispatcher: DefaultAppLifecycleDispatcher,
    var onBackPressEvent: () -> Unit = {}
)