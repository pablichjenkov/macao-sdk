package com.intervalintl.workflow

import android.support.v4.app.Fragment
import android.view.View


interface ViewBox {
    fun <F: Fragment> setView(fragment: F)
    fun <V: View> setView(view: V)
}