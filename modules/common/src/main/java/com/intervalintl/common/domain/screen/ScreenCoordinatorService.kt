package com.intervalintl.common.domain.screen

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.intervalintl.common.Constants
import com.intervalintl.common.StateService
import com.intervalintl.workflow.view.ScreenCoordinator


class ScreenCoordinatorService(private val fragmentManager: FragmentManager,
                               private val viewPortContainer: ViewGroup) : StateService {

    override fun getId(): String {
        return Constants.DEFAULT_SCREEN_SERVICE_ID
    }

    fun getFlowViewPort(): ScreenCoordinator {
        return ScreenCoordinatorImpl(fragmentManager, viewPortContainer)
    }
}