package com.intervalintl.workflow.view.delegate

import android.view.ViewGroup
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.CoordinatorActivity
import com.intervalintl.workflow.view.CoordinatorBindableView


class CoordinatorViewGroupBinder<F : Coordinator<*>>(
        private val viewGroup: ViewGroup,
        private val callback: Callback<F>) : CoordinatorBindableView {

    private lateinit var flowId: String

    fun onRestoreInstanceState(viewState: CoordinatorViewGroupSavedState) {
        flowId = viewState.flowId
    }

    fun onAttachedToWindow() {

        val activity = viewGroup.context as? CoordinatorActivity<*>
                ?: throw RuntimeException("ViewGroups that implement CoordinatorBindableView must be " +
                "used in an instance of CoordinatorActivity")

        val flow: F? = activity.findFlowById(flowId)
        if (flow == null) {
            throw RuntimeException("Coordinator: " + flowId + " not found in the FlowTree. You missed " +
                    "to assign a flowId to this ViewGroup or to attached the Coordinator in the same " +
                    "CoordinatorActivity where this ViewGroup belongs to.")
        }

        callback.onFlowBound(flow)
    }

    fun onSaveInstanceState(viewState: CoordinatorViewGroupSavedState) {
        viewState.flowId = flowId
    }

    override fun setFlowId(flowId: String) {
        this.flowId = flowId
    }


    interface Callback<U> {
        fun onFlowBound(viewModelInstance: U)
    }

}
