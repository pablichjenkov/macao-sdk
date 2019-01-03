package com.ncl.common.domain.screen

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


interface ScreenCoordinator {

    fun onConfigurationChange(fragmentManager: FragmentManager, viewPortContainer: ViewGroup)

    fun <F> push(fragment: F, fragmentTag: String) where F: Fragment
    fun <V> push(view: V) where V: View

    fun <F> pop(fragment: F) where F: Fragment
    fun <V> pop(view: V) where V: View

    fun <F> setView(fragment: F, fragmentTag: String) where F: Fragment
    fun <V> setView(view: V) where V: View

}