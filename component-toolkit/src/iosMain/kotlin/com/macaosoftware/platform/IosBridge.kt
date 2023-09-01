package com.macaosoftware.platform

import com.macaosoftware.platform.DefaultAppLifecycleDispatcher
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "IosBridge", exact = true)
class IosBridge(
    var appLifecycleDispatcher: DefaultAppLifecycleDispatcher
)
