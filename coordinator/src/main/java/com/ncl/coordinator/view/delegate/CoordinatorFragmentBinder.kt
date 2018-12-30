package com.ncl.coordinator.view.delegate

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorActivity
import com.ncl.coordinator.CoordinatorProvider
import com.ncl.coordinator.view.CoordinatorBindableView


class CoordinatorFragmentBinder<C : Coordinator>(
        private val fragment: Fragment,
        private val callback: Callback<C>) : CoordinatorBindableView {

    private lateinit var coordinatorId: String


    override fun setCoordinatorId(coordinatorId: String) {
        this.coordinatorId = coordinatorId
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            coordinatorId = savedInstanceState.getString(KEY_FLOW_ID)
        }
    }

    fun onResume() {

        val coordinatorProvider = fragment.activity
                as? CoordinatorProvider
                ?: throw RuntimeException("Fragments that implement CoordinatorBindableView must " +
                        "be used in an Activity that implements CoordinatorProvider interface.")

        coordinatorProvider.getCoordinatorById<C>(coordinatorId)?.let { coordinator ->

            callback.onCoordinatorBound(coordinator)

        } ?: throw RuntimeException("Coordinator: " + coordinatorId + " not found in the Coordinators" +
                "Tree. You missed to assign the coordinatorId associated with this Fragment " +
                "or attach the Coordinator in a Parent Coordinator that belongs to Activity: " +
                fragment.activity?.javaClass?.simpleName)
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_FLOW_ID, coordinatorId)
    }


    interface Callback<in O> {
        fun onCoordinatorBound(coordinator: O)
    }

    companion object {
        private val KEY_FLOW_ID = "key_flow_id"
    }

}
