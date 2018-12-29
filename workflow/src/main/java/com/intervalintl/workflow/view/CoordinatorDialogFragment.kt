package com.intervalintl.workflow.view

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.view.delegate.CoordinatorFragmentBinder


abstract class CoordinatorDialogFragment<F : Coordinator<*>> : DialogFragment(), CoordinatorBindableView {

    private lateinit var coordinatorFragmentBinder: CoordinatorFragmentBinder<F>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coordinatorFragmentBinder = CoordinatorFragmentBinder(
                this,
                object : CoordinatorFragmentBinder.Callback<F> {
                    override fun onFlowBound(flow: F) {
                        this@CoordinatorDialogFragment.onFlowBound(flow)
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
