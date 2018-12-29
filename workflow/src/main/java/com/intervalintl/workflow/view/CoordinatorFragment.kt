package com.intervalintl.workflow.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.view.delegate.CoordinatorFragmentBinder


abstract class CoordinatorFragment<F : Coordinator<*>> : Fragment(), CoordinatorBindableView {

    private val coordinatorFragmentBinder = CoordinatorFragmentBinder(
            this@CoordinatorFragment,
            object : CoordinatorFragmentBinder.Callback<F> {
                override fun onFlowBound(flow: F) {
                    this@CoordinatorFragment.onFlowBound(flow)
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
