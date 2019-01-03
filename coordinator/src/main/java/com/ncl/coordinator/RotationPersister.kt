package com.ncl.coordinator

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RotationPersister constructor(fragmentActivity: FragmentActivity) {

    private val coordinatorStore: CoordinatorStore

    init {
        val viewModelProvider = ViewModelProvider(fragmentActivity, ViewModelProvider.NewInstanceFactory())
        coordinatorStore = viewModelProvider.get(COORDINATOR_STORE_VIEW_MODEL_ID, CoordinatorStore::class.java)
    }

    fun <C : Coordinator> getRootCoordinator(): C? {
        return coordinatorStore.rootCoordinator as C?
    }

    fun setRootCoordinator(rootCoordinator: Coordinator) {
        coordinatorStore.rootCoordinator = rootCoordinator
    }

    fun <C : Coordinator> findCoordinatorById(flowId: String): C? {
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
