package com.intervalintl.workflow.view.delegate

import android.os.Bundle
import android.support.v4.app.Fragment
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.FlowActivity
import com.intervalintl.workflow.view.FlowBindableView


class FlowFragmentBinder<F : Flow<*, *>>(
        private val fragment: Fragment,
        private val callback: Callback<F>) : FlowBindableView {

    private lateinit var flowId: String

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            flowId = savedInstanceState.getString(KEY_FLOW_ID)
        }
    }

    fun onResume() {

        val activity = fragment.activity

        if (activity is FlowActivity<*> == false) {
            throw RuntimeException("Fragments that implements FlowBindableView must be attached to "
                    + " an implementation of FlowActivity.")
        }

        val flowActivity: FlowActivity<*> = activity as FlowActivity<*>

        val flow: F? = flowActivity.findFlowById(flowId)
        if(flow == null) {
            throw RuntimeException("Flow: " + flowId + " not found in the FlowTree. You missed " +
                    "to assign the flowId associated with this Fragment or attach the Flow in " +
                    "the Parent Flow that belongs to Activity: " + activity.javaClass.simpleName)
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
