package com.pablichj.incubator.uistate3.node

class DefaultBackPressDispatcher : IBackPressDispatcher {

    private val onBackPressedCallbacks: ArrayDeque<DefaultBackPressedCallbackProxy> = ArrayDeque()

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        val backPressCBProxy = DefaultBackPressedCallbackProxy(backPressedCallback, true)
        if (!onBackPressedCallbacks.contains(backPressCBProxy)) {
            onBackPressedCallbacks.add(backPressCBProxy)
        }
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        onBackPressedCallbacks.remove(
            DefaultBackPressedCallbackProxy(backPressedCallback, true)
        )
    }

    fun dispatchBackPressed() {
        onBackPressedCallbacks.lastOrNull { it.isEnabled }?.handleOnBackPressed()
    }

}

private class DefaultBackPressedCallbackProxy(
    private val backPressedCallback: BackPressedCallback,
    var isEnabled: Boolean
) {

    init {
        backPressedCallback.onEnableChanged = {
            this.isEnabled = it
        }
    }

    fun handleOnBackPressed() {
        backPressedCallback.onBackPressed()
    }

    override fun equals(other: Any?): Boolean {
        val otherDefaultBackPressedCallbackProxy =
            other as? DefaultBackPressedCallbackProxy ?: return false
        return this.backPressedCallback == otherDefaultBackPressedCallbackProxy.backPressedCallback
    }

    override fun hashCode(): Int {
        var result = backPressedCallback.hashCode()
        result = 31 * result + isEnabled.hashCode()
        return result
    }

}