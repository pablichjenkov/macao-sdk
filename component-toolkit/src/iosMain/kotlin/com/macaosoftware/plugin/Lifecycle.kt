package com.macaosoftware.plugin

import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplicationDidBecomeActiveNotification
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillTerminateNotification
import platform.darwin.NSObjectProtocol

class Lifecycle {

    private val platformLifecyclePlugin = DefaultPlatformLifecyclePlugin()

    private lateinit var didEnterBackgroundNotificationObserver: NSObjectProtocol
    private lateinit var didBecomeActiveNotificationObserver: NSObjectProtocol
    private lateinit var willTerminateNotificationObserver: NSObjectProtocol

    init {
        startObserving()
        platformLifecyclePlugin.dispatchAppLifecycleEvent(AppLifecycleEvent.Start)
    }

    private fun startObserving() {
        didBecomeActiveNotificationObserver = addObserverFor(UIApplicationDidBecomeActiveNotification) {
            println("LifecycleOwner::DidBecomeActive")
            started()
        }
        didEnterBackgroundNotificationObserver = addObserverFor(UIApplicationDidEnterBackgroundNotification) {
            println("LifecycleOwner::DidEnterBackground")
            stopped()
        }
        willTerminateNotificationObserver = addObserverFor(UIApplicationWillTerminateNotification) {
            println("LifecycleOwner::WillTerminate")
        }
    }

    private fun addObserverFor(
        notification: platform.Foundation.NSNotificationName,
        block: () -> Unit
    ): NSObjectProtocol {
        return NSNotificationCenter.defaultCenter.addObserverForName(
            name = notification,
            `object` = null,
            queue = NSOperationQueue.mainQueue,
            usingBlock = {
                block()
            }
        )
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
