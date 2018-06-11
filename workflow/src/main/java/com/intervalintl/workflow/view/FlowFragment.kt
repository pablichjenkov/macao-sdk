package com.intervalintl.workflow.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.view.delegate.FlowFragmentBinder


abstract class FlowFragment<F : Flow<*, *>> : Fragment(), FlowBindableView {

    private val coordinatorFragmentBinder = FlowFragmentBinder(
            this@FlowFragment,
            object : FlowFragmentBinder.Callback<F> {
                override fun onFlowBound(flow: F) {
                    this@FlowFragment.onFlowBound(flow)
                }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coordinatorFragmentBinder.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        coordinatorFragmentBinder.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        coordinatorFragmentBinder.onSaveInstanceState(outState)
    }

    override fun setFlowId(flowId: String) {
        coordinatorFragmentBinder.setFlowId(flowId)
    }

    protected abstract fun onFlowBound(flow: F)

}
