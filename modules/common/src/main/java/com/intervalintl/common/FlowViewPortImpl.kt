package com.intervalintl.common

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import com.intervalintl.workflow.view.FlowBindableView
import com.intervalintl.workflow.view.FlowViewPort


class FlowViewPortImpl constructor(private val fragmentManager: FragmentManager,
                                   private val viewPortContainer: ViewGroup) : FlowViewPort {

    @IdRes val viewPortContainerId: Int = viewPortContainer.id


    override fun <F> setView(fragment: F, fragmentTag: String)
            where F : Fragment, F : FlowBindableView {

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(viewPortContainerId, fragment, fragmentTag)
        fragmentTransaction.commitNow()
    }

    override fun <V> setView(view: V) where V : View, V : FlowBindableView {
        viewPortContainer.removeAllViews()
        viewPortContainer.addView(view)
    }

}