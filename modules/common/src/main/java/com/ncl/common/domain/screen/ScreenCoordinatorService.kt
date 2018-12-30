package com.ncl.common.domain.screen

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.ncl.common.Constants
import com.ncl.common.StateService


class ScreenCoordinatorService(private val fragmentManager: FragmentManager,
                               private val viewPortContainer: ViewGroup) : StateService {

    override fun getId(): String {
        return Constants.DEFAULT_SCREEN_SERVICE_ID
    }

    fun getFlowViewPort(): ScreenCoordinator {
        return ScreenCoordinatorImpl(fragmentManager, viewPortContainer)
    }
}