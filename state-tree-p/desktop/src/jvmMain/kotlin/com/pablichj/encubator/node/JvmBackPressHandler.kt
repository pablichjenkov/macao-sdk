package com.pablichj.encubator.node

import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.IBackPressDispatcher

class JvmBackPressDispatcher(
) : IBackPressDispatcher {

    override fun subscribe(backPressedCallback: BackPressedCallback) {
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        backPressedCallback.onEnableChanged?.invoke(false)
    }

}

class JvmBackPressedCallback(
    private val backPressedCallback: BackPressedCallback,
    enabled: Boolean
)