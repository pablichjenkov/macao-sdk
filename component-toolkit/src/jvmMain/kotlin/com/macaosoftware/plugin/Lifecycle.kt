package com.macaosoftware.plugin

import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class Lifecycle(
    private val coroutineScope: CoroutineScope,
    private val windowState: WindowState
) {

    private val platformLifecyclePlugin = DefaultPlatformLifecyclePlugin()

    init {
        startObserving()
    }

    private fun startObserving() {

        coroutineScope.launch {
            snapshotFlow { windowState.isMinimized }
                .onEach { minimized ->
                    println("Receiving windowState.isMinimized = $minimized")
                    if (minimized) {
                        stopped()
                    } else {
                        started()
                    }
                }
                .launchIn(this)
        }
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
