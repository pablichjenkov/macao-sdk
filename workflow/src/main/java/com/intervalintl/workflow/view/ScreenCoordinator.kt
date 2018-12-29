package com.intervalintl.workflow.view

import android.view.View
import androidx.fragment.app.Fragment


interface ScreenCoordinator {
    fun <F> setView(fragment: F, fragmentTag: String) where F: CoordinatorBindableView, F: Fragment
    fun <V> setView(view: V) where V: CoordinatorBindableView, V: View
}