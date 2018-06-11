package com.intervalintl.workflow.common

import android.support.v4.app.FragmentManager
import android.view.ViewGroup
import com.intervalintl.workflow.State
import com.intervalintl.workflow.view.FlowViewPort


class FlowViewPortService(private val fragmentManager: FragmentManager,
                          private val viewPortContainer: ViewGroup) : State {

    override fun getId(): String {
        return Constants.DEFAULT_FLOW_VIEWPORT_SERVICE_ID
    }

    fun getFlowViewPort(): FlowViewPort {
        return FlowViewPortImpl(fragmentManager, viewPortContainer)
    }
}