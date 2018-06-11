package com.intervalintl.workflow.view.delegate

import android.view.ViewGroup
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.FlowActivity
import com.intervalintl.workflow.view.FlowBindableView


class FlowViewGroupBinder<F : Flow<*, *>>(
        private val viewGroup: ViewGroup,
        private val callback: Callback<F>) : FlowBindableView {

    private lateinit var flowId: String

    fun onRestoreInstanceState(viewState: FlowViewGroupSavedState) {
        flowId = viewState.flowId
    }

    fun onAttachedToWindow() {

        val activity = viewGroup.context as? FlowActivity<*>
                ?: throw RuntimeException("ViewGroups that implement FlowBindableView must be " +
                "used in an instance of FlowActivity")

        val flow: F? = activity.findFlowById(flowId)
        if (flow == null) {
            throw RuntimeException("Flow: " + flowId + " not found in the FlowTree. You missed " +
                    "to assign a flowId to this ViewGroup or to attached the Flow in the same " +
                    "FlowActivity where this ViewGroup belongs to.")
        }

        callback.onFlowBound(flow)
    }

    fun onSaveInstanceState(viewState: FlowViewGroupSavedState) {
        viewState.flowId = flowId
    }

    override fun setFlowId(flowId: String) {
        this.flowId = flowId
    }


    interface Callback<U> {
        fun onFlowBound(viewModelInstance: U)
    }

}
