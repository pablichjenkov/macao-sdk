package com.intervalintl.workflow

import android.content.Intent


/**
 * Don't keep strong references to Views, Activities or Fragments in this class, this class persist
 * Configuration Changes so retaining references will cause memory leaks.
 */
abstract class Coordinator<StateContext> {

    val flowId: String
    private var stateContextSnapshot: StateContext? = null


    constructor(flowId: String) {
        this.flowId = flowId
    }


    // region: Coordinator Tree Events

    internal open fun <F : Coordinator<StateContext>> depthFirstSearchFlowById(subFlowId: String)
            : F? {

        if (this.flowId.equals(subFlowId)) {
            return this as F
        }

        return null
    }

    internal open fun dispatchStateContextUpdate(stateContext: StateContext) {

        // save a snapshot of StateContext to be provided later to a child when it attaches.
        stateContextSnapshot = stateContext

        // consume the StateContext and put into it any dependency this flow provide
        onStateContextUpdate(stateContext)

    }

    // endregion


    // region: abstract Coordinator API, to be override

    abstract fun onStateContextUpdate(stateContext: StateContext)

    abstract fun start()

    abstract fun stop()

    // endregion


    // region: Activity Lifecycle Events

    open fun onCreate() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) : Boolean {
        return false
    }

    open fun onDestroy() {}

    open fun onBackPressed() : Boolean {
        return false
    }

    // endregion

}
