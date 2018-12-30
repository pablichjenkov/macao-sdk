package com.ncl.coordinator

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RotationPersister internal constructor(activity: FragmentActivity) {

    private val coordinatorStore: CoordinatorStore

    init {
        val viewModelProvider = ViewModelProvider(activity, ViewModelProvider.NewInstanceFactory())
        coordinatorStore = viewModelProvider.get(COORDINATOR_STORE_VIEW_MODEL_ID, CoordinatorStore::class.java)
    }

    fun <F : Coordinator> getRootCoordinator(): F? {
        return coordinatorStore.rootCoordinator as F?
    }

    fun setRootCoordinator(rootCoordinator: Coordinator) {
        coordinatorStore.rootCoordinator = rootCoordinator
    }

    fun <F : Coordinator> findCoordinatorById(flowId: String): F? {
        return coordinatorStore.rootCoordinator?.depthFirstSearchById(flowId)
    }

    class CoordinatorStore : ViewModel() {

        var rootCoordinator: Coordinator? = null

        override fun onCleared() {
            rootCoordinator?.stop()
        }

    }

    companion object {
        private val COORDINATOR_STORE_VIEW_MODEL_ID = "coordinatorStoreViewModel"
    }

}
