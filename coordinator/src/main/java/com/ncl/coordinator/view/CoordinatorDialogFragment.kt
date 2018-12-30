package com.ncl.coordinator.view

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.view.delegate.CoordinatorFragmentBinder


abstract class CoordinatorDialogFragment<C : Coordinator> : DialogFragment(), CoordinatorBindableView {

    private lateinit var coordinatorFragmentBinder: CoordinatorFragmentBinder<C>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coordinatorFragmentBinder = CoordinatorFragmentBinder(
                this,
                object : CoordinatorFragmentBinder.Callback<C> {
                    override fun onCoordinatorBound(coordinator: C) {
                        this@CoordinatorDialogFragment.onCoordinatorBound(coordinator)
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

    override fun setCoordinatorId(coordinatorId: String) {
        coordinatorFragmentBinder.setCoordinatorId(coordinatorId)
    }

    protected abstract fun onCoordinatorBound(coordinator: C)

}
