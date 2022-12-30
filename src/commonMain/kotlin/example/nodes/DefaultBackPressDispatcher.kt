package example.nodes

import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher

class DefaultBackPressDispatcher : IBackPressDispatcher {

    private val onBackPressedCallbacks: ArrayDeque<JvmBackPressedCallbackProxy> =
        ArrayDeque<JvmBackPressedCallbackProxy>()

    override fun subscribe(backPressedCallback: BackPressedCallback) {
        val jvmBackPressCBProxy = JvmBackPressedCallbackProxy(backPressedCallback, true)
        if (!onBackPressedCallbacks.contains(jvmBackPressCBProxy)) {
            onBackPressedCallbacks.add(jvmBackPressCBProxy)
        }
    }

    override fun unsubscribe(backPressedCallback: BackPressedCallback) {
        onBackPressedCallbacks.remove(
            JvmBackPressedCallbackProxy(backPressedCallback, true)
        )
    }

    fun dispatchBackPressed() {
        onBackPressedCallbacks.lastOrNull { it.isEnabled }?.handleOnBackPressed()
    }

}

internal class JvmBackPressedCallbackProxy(
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
        val otherJvmBackPressedCallbackProxy =
            other as? JvmBackPressedCallbackProxy ?: return false
        return this.backPressedCallback == otherJvmBackPressedCallbackProxy.backPressedCallback
    }

    override fun hashCode(): Int {
        var result = backPressedCallback.hashCode()
        result = 31 * result + isEnabled.hashCode()
        return result
    }

}