package com.macaosoftware.plugin

interface BackPressDispatcherPlugin {
    fun subscribe(backPressedCallback: BackPressedCallback)
    fun unsubscribe(backPressedCallback: BackPressedCallback)
    fun dispatchBackPressed()
}

class DefaultBackPressDispatcherPlugin : BackPressDispatcherPlugin {

    private val onBackPressedCallbacks: ArrayDeque<BackPressedCallbackProxy> = ArrayDeque()

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        val backPressCBProxy = BackPressedCallbackProxy(backPressedCallback, true)
        if (!onBackPressedCallbacks.contains(backPressCBProxy)) {
            onBackPressedCallbacks.add(backPressCBProxy)
        }
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        onBackPressedCallbacks.remove(
            BackPressedCallbackProxy(backPressedCallback, true)
        )
    }

    override fun dispatchBackPressed() {
        onBackPressedCallbacks.lastOrNull { it.isEnabled }?.handleOnBackPressed()
    }

}

abstract class BackPressedCallback {
    var onEnableChanged: ((Boolean) -> Unit)? = null
    abstract fun onBackPressed()
}

private class BackPressedCallbackProxy(
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
        val otherBackPressedCallbackProxy =
            other as? BackPressedCallbackProxy ?: return false
        return this.backPressedCallback == otherBackPressedCallbackProxy.backPressedCallback
    }

    override fun hashCode(): Int {
        var result = backPressedCallback.hashCode()
        result = 31 * result + isEnabled.hashCode()
        return result
    }

}

object EmptyBackPressCallback: BackPressedCallback() {
    override fun onBackPressed() {
        println("EmptyBackPressCallback::onBackPressed does nothing")
    }
}

class ForwardBackPressCallback(
    private val onBackPressedAction: () -> Unit
): BackPressedCallback() {
    override fun onBackPressed() {
        onBackPressedAction()
    }
}