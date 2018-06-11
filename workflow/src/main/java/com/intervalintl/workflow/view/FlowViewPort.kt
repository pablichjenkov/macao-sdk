package com.intervalintl.workflow.view

import android.support.v4.app.Fragment
import android.view.View


interface FlowViewPort {
    fun <F> setView(fragment: F, fragmentTag: String) where F: FlowBindableView, F: Fragment
    fun <V> setView(view: V) where V: FlowBindableView, V: View
}