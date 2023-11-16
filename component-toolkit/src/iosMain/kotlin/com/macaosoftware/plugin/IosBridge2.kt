package com.macaosoftware.plugin

import platform.Foundation.NSURL
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "IosBridge2", exact = true)
class IOSBridge2(
    var test: NSURL = NSURL(string = "google.com")
)
