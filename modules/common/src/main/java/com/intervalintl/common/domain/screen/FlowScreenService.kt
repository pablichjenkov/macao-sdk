package com.intervalintl.common.domain.screen

import android.support.v4.app.FragmentManager
import android.view.ViewGroup
import com.intervalintl.common.Constants
import com.intervalintl.common.StateService
import com.intervalintl.workflow.view.FlowScreen


class FlowScreenService(private val fragmentManager: FragmentManager,
                        private val viewPortContainer: ViewGroup) : StateService {

    override fun getId(): String {
        return Constants.DEFAULT_SCREEN_SERVICE_ID
    }

    fun getFlowViewPort(): FlowScreen {
        return FlowScreenImpl(fragmentManager, viewPortContainer)
    }
}