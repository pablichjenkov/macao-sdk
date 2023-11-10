package com.macaosoftware.component.demo

import com.macaosoftware.plugin.AppLifecycleDispatcher
import com.macaosoftware.plugin.DefaultAppLifecycleDispatcher

fun createDefaultAppLifecycleDispatcher(): AppLifecycleDispatcher {
    return DefaultAppLifecycleDispatcher()
}
