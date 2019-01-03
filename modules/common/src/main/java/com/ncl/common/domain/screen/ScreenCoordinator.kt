package com.ncl.common.domain.screen

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ncl.coordinator.Coordinator


abstract class ScreenCoordinator(coordinatorId: String) : Coordinator(coordinatorId) {

    abstract fun onConfigurationChange(fragmentManager: FragmentManager, viewPortContainer: ViewGroup)

    abstract fun <F> push(fragment: F, fragmentTag: String) where F: Fragment
    abstract fun <V> push(view: V) where V: View

    abstract fun <F> pop(fragment: F) where F: Fragment
    abstract fun <V> pop(view: V) where V: View

    abstract fun <F> setView(fragment: F, fragmentTag: String) where F: Fragment
    abstract fun <V> setView(view: V) where V: View

}