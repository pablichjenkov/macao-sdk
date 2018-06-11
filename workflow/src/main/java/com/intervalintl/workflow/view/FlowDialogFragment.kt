package com.intervalintl.workflow.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.view.delegate.FlowFragmentBinder


abstract class FlowDialogFragment<F : Flow<*, *>> : DialogFragment(), FlowBindableView {

    private lateinit var coordinatorFragmentBinder: FlowFragmentBinder<F>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coordinatorFragmentBinder = FlowFragmentBinder(
                this,
                object : FlowFragmentBinder.Callback<F> {
                    override fun onFlowBound(flow: F) {
                        this@FlowDialogFragment.onFlowBound(flow)
                    }
                })

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

    protected abstract fun onFlowBound(coordinatorViewModel: F)

}
