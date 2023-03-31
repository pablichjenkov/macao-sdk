package com.pablichj.incubator.uistate3

import com.pablichj.incubator.uistate3.platform.DefaultAppLifecycleDispatcher
import com.pablichj.incubator.uistate3.platform.SafeAreaInsets

class IosBridge(
    var appLifecycleDispatcher: DefaultAppLifecycleDispatcher,
    var safeAreaInsets: SafeAreaInsets
)