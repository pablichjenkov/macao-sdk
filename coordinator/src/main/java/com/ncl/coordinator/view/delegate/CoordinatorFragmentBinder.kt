package com.ncl.coordinator.view.delegate

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorActivity
import com.ncl.coordinator.view.CoordinatorBindableView


class CoordinatorFragmentBinder<C : Coordinator>(
        private val fragment: Fragment,
        private val callback: Callback<C>) : CoordinatorBindableView {

    private lateinit var coordinatorId: String

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            coordinatorId = savedInstanceState.getString(KEY_FLOW_ID)
        }
    }

    fun onResume() {

        val activity = fragment.activity

        if (activity is CoordinatorActivity == false) {
            return
        }

        val coordinatorActivity: CoordinatorActivity = activity as CoordinatorActivity

        val coordinator: C? = coordinatorActivity.findFlowById(coordinatorId)
        if(coordinator == null) {
            throw RuntimeException("Coordinator: " + coordinatorId + " not found in the FlowTree. You missed " +
                    "to assign the coordinatorId associated with this Fragment or attach the Coordinator in " +
                    "the Parent Coordinator that belongs to Activity: " + activity.javaClass.simpleName)
        }

        callback.onCoordinatorBound(coordinator)
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_FLOW_ID, coordinatorId)
    }

    override fun setCoordinatorId(coordinatorId: String) {
        this.coordinatorId = coordinatorId
    }


    interface Callback<in O> {
        fun onCoordinatorBound(coordinator: O)
    }

    companion object {
        private val KEY_FLOW_ID = "key_flow_id"
    }

}
