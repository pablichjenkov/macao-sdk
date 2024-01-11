package com.macaosoftware.plugin.backpress

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.macaosoftware.plugin.BackPressDispatcherPlugin
import com.macaosoftware.plugin.BackPressedCallback

class AndroidBackPressDispatcherPlugin(
    private val componentActivity: ComponentActivity,
) : BackPressDispatcherPlugin {

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        componentActivity.onBackPressedDispatcher.addCallback(
            componentActivity,
            AndroidBackPressedCallbackProxy(backPressedCallback, true)
        )
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        backPressedCallback.onEnableChanged?.invoke(false)
    }

    override fun dispatchBackPressed() {
        // No-op, the operating system will dispatch this event.
    }

}

internal class AndroidBackPressedCallbackProxy(
    private val backPressedCallback: BackPressedCallback,
    enabled: Boolean
) : OnBackPressedCallback(enabled) {

    init {
        this.isEnabled = enabled
        backPressedCallback.onEnableChanged = {
            this.isEnabled = it
        }
    }

    override fun handleOnBackPressed() {
        backPressedCallback.onBackPressed()
    }

}