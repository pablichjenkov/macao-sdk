package com.pablichj.incubator.uistate3.node.backpress

interface IBackPressDispatcher {
    fun subscribe(backPressedCallback: BackPressedCallback)
    fun unsubscribe(backPressedCallback: BackPressedCallback)
    fun isSystemBackButtonEnabled(): Boolean
}

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

    override fun isSystemBackButtonEnabled(): Boolean {
        return true
    }

    fun dispatchBackPressed() {
        onBackPressedCallbacks.lastOrNull { it.isEnabled }?.handleOnBackPressed()
    }

}

abstract class BackPressedCallback {
    var onEnableChanged: ((Boolean) -> Unit)? = null
    abstract fun onBackPressed()
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