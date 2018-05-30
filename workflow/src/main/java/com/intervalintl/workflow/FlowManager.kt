package com.intervalintl.workflow

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.FragmentActivity


class FlowManager internal constructor(activity: FragmentActivity) {
    private val flowStore: FlowStore


    init {
        val viewModelProvider = ViewModelProvider(activity, ViewModelProvider.NewInstanceFactory())
        flowStore = viewModelProvider.get(FLOW_STORE_VIEW_MODEL_ID, FlowStore::class.java)
    }

    fun <F : Flow<*, *>> getRootFlow(): F? {
        return flowStore.rootFlow as F?
    }

    fun setRootFlow(rootFlow: Flow<*, *>) {
        flowStore.rootFlow = rootFlow
    }

    fun <F : Flow<*, *>> findTraversalSubFlowById(flowId: String): F? {
        return flowStore.rootFlow?.findTraversalSubFlowById(flowId)
    }

    class FlowStore : ViewModel() {

        var rootFlow: Flow<*, *>? = null

        override fun onCleared() {
            rootFlow?.abort()
        }

    }

    companion object {
        private val FLOW_STORE_VIEW_MODEL_ID = "flowStoreViewModel"
    }

}
