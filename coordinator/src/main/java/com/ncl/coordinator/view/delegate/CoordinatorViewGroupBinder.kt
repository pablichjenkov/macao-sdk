package com.ncl.coordinator.view.delegate

import android.view.ViewGroup
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorActivity
import com.ncl.coordinator.view.CoordinatorBindableView


class CoordinatorViewGroupBinder<C : Coordinator>(
        private val viewGroup: ViewGroup,
        private val callback: Callback<C>) : CoordinatorBindableView {

    private lateinit var coordinatorId: String

    fun onRestoreInstanceState(viewState: CoordinatorViewGroupSavedState) {
        coordinatorId = viewState.coordinatorId
    }

    fun onAttachedToWindow() {

        val activity = viewGroup.context as? CoordinatorActivity
                ?: throw RuntimeException("ViewGroups that implement CoordinatorBindableView must be " +
                "used in an instance of CoordinatorActivity")

        val coordinator: C? = activity.findFlowById(coordinatorId)
        if (coordinator == null) {
            throw RuntimeException("Coordinator: " + coordinatorId + " not found in the FlowTree. You missed " +
                    "to assign a coordinatorId to this ViewGroup or to attached the Coordinator in the same " +
                    "CoordinatorActivity where this ViewGroup belongs to.")
        }

        callback.onCoordinatorBound(coordinator)
    }

    fun onSaveInstanceState(viewState: CoordinatorViewGroupSavedState) {
        viewState.coordinatorId = coordinatorId
    }

    override fun setCoordinatorId(coordinatorId: String) {
        this.coordinatorId = coordinatorId
    }


    interface Callback<U> {
        fun onCoordinatorBound(coordinator: U)
    }

}
