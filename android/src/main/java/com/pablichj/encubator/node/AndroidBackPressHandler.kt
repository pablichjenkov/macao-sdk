package com.pablichj.encubator.node

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

class AndroidBackPressDispatcher(
    private val activity: ComponentActivity,
) : IBackPressDispatcher {

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        activity.onBackPressedDispatcher.addCallback(
            activity,
            AndroidBackPressedCallback(backPressedCallback, true)
        )
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        backPressedCallback.onEnableChanged?.invoke(false)
    }

}

class AndroidBackPressedCallback(
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