package com.ncl.common.domain.screen

import android.view.View
import androidx.fragment.app.Fragment


interface ScreenCoordinator {
    fun <F> push(fragment: F, fragmentTag: String) where F: Fragment
    fun <V> push(view: V) where V: View

    fun <F> pop(fragment: F) where F: Fragment
    fun <V> pop(view: V) where V: View

    fun <F> setView(fragment: F, fragmentTag: String) where F: Fragment
    fun <V> setView(view: V) where V: View
}