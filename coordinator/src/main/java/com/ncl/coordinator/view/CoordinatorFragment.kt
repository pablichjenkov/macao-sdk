package com.ncl.coordinator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.view.delegate.CoordinatorFragmentBinder


abstract class CoordinatorFragment<F : Coordinator> : Fragment(), CoordinatorBindableView {

    private val coordinatorFragmentBinder = CoordinatorFragmentBinder(
            this@CoordinatorFragment,
            object : CoordinatorFragmentBinder.Callback<F> {
                override fun onCoordinatorBound(coordinator: F) {
                    this@CoordinatorFragment.onCoordinatorBound(coordinator)
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

    override fun setCoordinatorId(coordinatorId: String) {
        coordinatorFragmentBinder.setCoordinatorId(coordinatorId)
    }

    protected abstract fun onCoordinatorBound(coordinator: F)

}
