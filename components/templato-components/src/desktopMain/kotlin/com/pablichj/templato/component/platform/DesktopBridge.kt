package com.pablichj.templato.component.platform

import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher

class DesktopBridge (
    var appLifecycleDispatcher: DefaultAppLifecycleDispatcher,
    var onBackPressEvent: () -> Unit = {}
)