package com.ncl.common.domain.screen

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class ScreenCoordinatorImpl constructor(private var fragmentManager: FragmentManager,
                                        private var viewPortContainer: ViewGroup

) : ScreenCoordinator {

    @IdRes var viewPortContainerId: Int = viewPortContainer.id


    override fun onConfigurationChange(fragmentManager: FragmentManager,
                                       viewPortContainer: ViewGroup
    ) {
        this.fragmentManager = fragmentManager
        this.viewPortContainer = viewPortContainer
        this.viewPortContainerId = viewPortContainer.id
    }

    override fun <F> push(fragment: F, fragmentTag: String) where F : Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(viewPortContainerId, fragment, fragmentTag)
        fragmentTransaction.commitNow()
    }

    override fun <V> push(view: V) where V : View {
        viewPortContainer.addView(view)
    }

    override fun <F> pop(fragment: F) where F : Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commitNow()
    }

    override fun <V> pop(view: V) where V : View {
        viewPortContainer.removeViewAt(viewPortContainer.childCount - 1)
    }

    override fun <F> setView(fragment: F, fragmentTag: String) where F : Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(viewPortContainerId, fragment, fragmentTag)
        fragmentTransaction.commitNow()
    }

    override fun <V> setView(view: V) where V : View {
        viewPortContainer.removeAllViews()
        viewPortContainer.addView(view)
    }

}