package com.pablichj.templato.component.core.backpress

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

class AndroidBackPressDispatcher(
    private val componentActivity: ComponentActivity,
) : IBackPressDispatcher {

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        componentActivity.onBackPressedDispatcher.addCallback(
            componentActivity,
            AndroidBackPressedCallbackProxy(backPressedCallback, true)
        )
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        backPressedCallback.onEnableChanged?.invoke(false)
    }

    override fun isSystemBackButtonEnabled(): Boolean {
        val navigationMode = SystemNavigationUtil.getNavigationBarInteractionMode(componentActivity)
        return navigationMode != SystemNavigationUtil.NAVIGATION_BAR_INTERACTION_MODE_GESTURE
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