package com.macaosoftware.plugin

import kotlin.native.ObjCName

@ObjCName("AppLifecycleDispatcher", exact = true)
interface PlatformLifecyclePlugin {
    fun subscribe(appLifecycleCallback: AppLifecycleCallback)
    fun unsubscribe(appLifecycleCallback: AppLifecycleCallback)
    fun dispatchAppLifecycleEvent(appLifecycleEvent: AppLifecycleEvent)
}

@ObjCName("AppLifecycleEvent", exact = true)
enum class AppLifecycleEvent {
    // @ObjCName("Start"), if not used is exported as start
    Start,
    // @ObjCName("Stop"), if not used is exported as stop
    Stop
}

interface AppLifecycleCallback {
    fun onEvent(appLifecycleEvent: AppLifecycleEvent)
}

@ObjCName(name = "DefaultAppLifecycleDispatcher", exact = true)
class DefaultPlatformLifecyclePlugin : PlatformLifecyclePlugin {

    private val appLifecycleCallbacks: ArrayDeque<AppLifecycleCallback> = ArrayDeque()
    private var lastEvent: AppLifecycleEvent? = null

    override fun subscribe(appLifecycleCallback: AppLifecycleCallback) {
        if (!appLifecycleCallbacks.contains(appLifecycleCallback)) {
            appLifecycleCallbacks.add(appLifecycleCallback)
        }
        lastEvent?.let { appLifecycleCallback.onEvent(it) }
    }

    override fun unsubscribe(appLifecycleCallback: AppLifecycleCallback) {
        appLifecycleCallbacks.remove(appLifecycleCallback)
    }

    override fun dispatchAppLifecycleEvent(appLifecycleEvent: AppLifecycleEvent) {
        println("DefaultAppLifecycleDispatcher::dispatchAppLifecycleEvent(${appLifecycleEvent})")
        lastEvent = appLifecycleEvent
        appLifecycleCallbacks.forEach { it.onEvent(appLifecycleEvent) }
    }

}
