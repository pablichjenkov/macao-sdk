package com.pablichj.incubator.uistate3.platform

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

interface IAppLifecycleDispatcher {
    fun subscribe(appLifecycleCallback: AppLifecycleCallback)
    fun unsubscribe(appLifecycleCallback: AppLifecycleCallback)
}

@OptIn(ExperimentalObjCName::class)
@ObjCName("AppLifecycleEvent")// Does not work
enum class AppLifecycleEvent {
    @ObjCName("Start")// Does work!
    Start,
    @ObjCName("Stop")
    Stop
}

abstract class AppLifecycleCallback {
    abstract fun onEvent(appLifecycleEvent: AppLifecycleEvent)
}

class DefaultAppLifecycleDispatcher : IAppLifecycleDispatcher {

    private val appLifecycleCallbacks: ArrayDeque<AppLifecycleCallback> = ArrayDeque()
    private var lastEvent: AppLifecycleEvent? = null

    override fun subscribe(appLifecycleCallback: AppLifecycleCallback) {
        println("DefaultAppLifecycleDispatcher::subscribe()")
        if (!appLifecycleCallbacks.contains(appLifecycleCallback)) {
            appLifecycleCallbacks.add(appLifecycleCallback)
        }
        lastEvent?.let { appLifecycleCallback.onEvent(it) }
    }

    override fun unsubscribe(appLifecycleCallback: AppLifecycleCallback) {
        appLifecycleCallbacks.remove(appLifecycleCallback)
    }

    fun dispatchAppLifecycleEvent(appLifecycleEvent: AppLifecycleEvent) {
        println("DefaultAppLifecycleDispatcher::dispatchAppLifecycleEvent(${appLifecycleEvent})")
        lastEvent = appLifecycleEvent
        appLifecycleCallbacks.forEach { it.onEvent(appLifecycleEvent) }
    }

}

object EmptyAppLifecycleCallback : AppLifecycleCallback() {
    override fun onEvent(appLifecycleEvent: AppLifecycleEvent) {
        println("EmptyAppLifecycleCallback::onEvent(${appLifecycleEvent}) does nothing")
    }
}

class ForwardAppLifecycleCallback(
    private val onAppLifecycleEvent: (appLifecycleEvent: AppLifecycleEvent) -> Unit
): AppLifecycleCallback() {
    override fun onEvent(appLifecycleEvent: AppLifecycleEvent) {
        println("ForwardAppLifecycleCallback::onEvent(${appLifecycleEvent}) does nothing")
        onAppLifecycleEvent(appLifecycleEvent)
    }
}