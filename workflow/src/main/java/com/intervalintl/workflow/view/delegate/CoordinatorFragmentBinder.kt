package com.intervalintl.workflow.view.delegate

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.CoordinatorActivity
import com.intervalintl.workflow.view.CoordinatorBindableView


class CoordinatorFragmentBinder<F : Coordinator<*>>(
        private val fragment: Fragment,
        private val callback: Callback<F>) : CoordinatorBindableView {

    private lateinit var flowId: String

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            flowId = savedInstanceState.getString(KEY_FLOW_ID)
        }
    }

    fun onResume() {

        val activity = fragment.activity

        if (activity is CoordinatorActivity<*> == false) {
            throw RuntimeException("Fragments that implements CoordinatorBindableView must be attached to "
                    + " an implementation of CoordinatorActivity.")
        }

        val coordinatorActivity: CoordinatorActivity<*> = activity as CoordinatorActivity<*>

        val flow: F? = coordinatorActivity.findFlowById(flowId)
        if(flow == null) {
            throw RuntimeException("Coordinator: " + flowId + " not found in the FlowTree. You missed " +
                    "to assign the flowId associated with this Fragment or attach the Coordinator in " +
                    "the Parent Coordinator that belongs to Activity: " + activity.javaClass.simpleName)
        }

        callback.onFlowBound(flow)
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_FLOW_ID, flowId)
    }

    override fun setFlowId(flowId: String) {
        this.flowId = flowId
    }


    interface Callback<in O> {
        fun onFlowBound(flow: O)
    }

    companion object {
        private val KEY_FLOW_ID = "key_flow_id"
    }

}
