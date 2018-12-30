package com.ncl.coordinator.view.delegate

import android.view.ViewGroup
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorProvider
import com.ncl.coordinator.view.CoordinatorBindableView


class CoordinatorViewGroupBinder<C : Coordinator>(
        private val viewGroup: ViewGroup,
        private val callback: Callback<C>) : CoordinatorBindableView {

    private lateinit var coordinatorId: String

    fun onRestoreInstanceState(viewState: CoordinatorViewGroupSavedState) {
        coordinatorId = viewState.coordinatorId
    }

    fun onAttachedToWindow() {

        val coordinatorProvider = viewGroup.context as? CoordinatorProvider
                ?: throw RuntimeException("ViewGroups that implement CoordinatorBindableView " +
                        "must be used in an Activity that implements Coordinator interface")

        coordinatorProvider.getCoordinatorById<C>(coordinatorId)?.let { coordinator ->

            callback.onCoordinatorBound(coordinator)

        } ?: throw RuntimeException("Coordinator: " + coordinatorId + " not found in the Coordinators" +
                " Tree. You missed to assign a coordinatorId to this ViewGroup or to attached the " +
                "Coordinator to a parent coordinator that lives in the same Activity that holds" +
                "this ViewGroup.")
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
