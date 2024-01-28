package com.macaosoftware.plugin

class Lifecycle {

    private val platformLifecyclePlugin = DefaultPlatformLifecyclePlugin()

    init {
        startObserving()
    }

    private fun startObserving() {
        started()
    }

    fun subscribe(appLifecycleCallback: AppLifecycleCallback) {
        platformLifecyclePlugin.subscribe(appLifecycleCallback)
    }

    private fun started() {
        platformLifecyclePlugin.dispatchAppLifecycleEvent(AppLifecycleEvent.Start)
    }

    private fun stopped() {
        platformLifecyclePlugin.dispatchAppLifecycleEvent(AppLifecycleEvent.Stop)
    }

    fun unsubscribe(appLifecycleCallback: AppLifecycleCallback) {
        platformLifecyclePlugin.unsubscribe(appLifecycleCallback)
    }
}
