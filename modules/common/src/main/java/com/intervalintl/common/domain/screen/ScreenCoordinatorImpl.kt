package com.intervalintl.common.domain.screen

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.intervalintl.workflow.view.CoordinatorBindableView
import com.intervalintl.workflow.view.ScreenCoordinator


class ScreenCoordinatorImpl constructor(private val fragmentManager: FragmentManager,
                                        private val viewPortContainer: ViewGroup) : ScreenCoordinator {

    @IdRes val viewPortContainerId: Int = viewPortContainer.id


    override fun <F> setView(fragment: F, fragmentTag: String)
            where F : Fragment, F : CoordinatorBindableView {

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(viewPortContainerId, fragment, fragmentTag)
        fragmentTransaction.commitNow()
    }

    override fun <V> setView(view: V) where V : View, V : CoordinatorBindableView {
        viewPortContainer.removeAllViews()
        viewPortContainer.addView(view)
    }

}