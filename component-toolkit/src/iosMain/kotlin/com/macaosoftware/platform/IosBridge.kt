package com.macaosoftware.platform

import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "IosBridge", exact = true)
class IosBridge(
    var appLifecycleDispatcher: AppLifecycleDispatcher
)
