package com.macaosoftware.component.demo

import com.macaosoftware.plugin.PlatformLifecyclePlugin
import com.macaosoftware.plugin.DefaultPlatformLifecyclePlugin

fun createDefaultAppLifecycleDispatcher(): PlatformLifecyclePlugin {
    return DefaultPlatformLifecyclePlugin()
}
