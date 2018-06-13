package com.intervalintl.workflow

import android.content.Intent
import android.support.annotation.CallSuper


/**
 * Don't keep strong references to Views, Activities or Fragments in this class, this class persist
 * Configuration Changes so retaining references will cause memory leaks.
 */
abstract class CompoundFlow<StateContext : Any, Broadcast: Any> : Flow<StateContext, Broadcast> {

    private var children: MutableList<Flow<StateContext, *>>? = null
    private var stateContextSnapshot: StateContext? = null


    constructor(flowId: String) : super(flowId)

    constructor(flowId: String, children: MutableList<Flow<StateContext, *>>) : super(flowId) {
        this.children = children
    }


    // region: CompoundFlow Tree Events

    protected fun attachAndStartChildFlow(childFlow: Flow<StateContext, *>) {
        attachChildFlow(childFlow)?.let {
            if (it) {
                childFlow.start()
            }
        }
    }

    protected fun attachChildFlow(childFlow: Flow<StateContext, *>) : Boolean? {
        if (children == null) {
            children = ArrayList()
        }

        stateContextSnapshot?.let {
            childFlow.dispatchStateContextUpdate(it)
        }

        return children?.add(childFlow)
    }

    fun <F : CompoundFlow<*, *>> getChildFlowById(flowId: String): F? {

        return children?.let {
            var result: F? = null

            for (child in it) {
                if (child.flowId.equals(flowId)) {
                    result = child as F
                    break
                }
            }

            return result

        }

    }

    override fun <F : Flow<StateContext, *>> depthFirstSearchFlowById(subFlowId: String): F? {

        if (this.flowId.equals(subFlowId)) {
            return this as F
        }

        return children?.let {
            var result: Flow<*, *>? = null

            for (childFlow in it) {
                result = childFlow.depthFirstSearchFlowById(subFlowId)

                if (result != null) {
                    return result as F
                }
            }

            return null

        }

    }

    override fun dispatchStateContextUpdate(stateContext: StateContext) {

        // save a snapshot of StateContext to be provided later to a child when it attaches.
        stateContextSnapshot = stateContext

        // consume the StateContext and put into it any dependency this flow provide
        onStateContextUpdate(stateContext)

        // forward the stateContext to children flows.
        children?.forEach { it.dispatchStateContextUpdate(stateContext) }
    }

    // endregion


    // region: Activity Lifecycle Events

    @CallSuper
    override fun onCreate() {
        children?.let {
            for (childFlow in it) {
                childFlow.onCreate()
            }
        }
    }

    @CallSuper
    override fun onResume() {
        children?.let {
            for (childFlow in it) {
                childFlow.onResume()
            }
        }
    }

    @CallSuper
    override fun onPause() {
        children?.let {
            for (childFlow in it) {
                childFlow.onPause()
            }
        }
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onActivityResult(requestCode, resultCode, data)
            }
            return consumed

        } ?: false

    }

    @CallSuper
    override fun onDestroy() {
        children?.let {
            for (childFlow in it) {
                childFlow.onDestroy()
            }
        }
    }

    @CallSuper
    override fun onBackPressed() : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onBackPressed()
            }
            return consumed

        } ?: false

    }

    // endregion

}