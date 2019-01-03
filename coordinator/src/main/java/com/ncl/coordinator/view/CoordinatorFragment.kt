package com.ncl.coordinator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.view.delegate.CoordinatorFragmentBinder


abstract class CoordinatorFragment<C : Coordinator> : Fragment(), CoordinatorBindableView {

    private val coordinatorFragmentBinder = CoordinatorFragmentBinder(this@CoordinatorFragment,
            object : CoordinatorFragmentBinder.Callback<C> {
                override fun onCoordinatorBound(coordinator: C) {
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

    /**
     * This method will be called every time this Fragment instance resumes. It will be called
     * after Fragment.onResume() method. If there is a Coordinator in the Coordinators Tree
     * matching the coordinatorId corresponding to this Fragment, it will be supplied on this
     * method. otherwise an exception will be thrown.
     * */
    protected abstract fun onCoordinatorBound(coordinator: C)

}
