package com.intervalintl.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intervalintl.workflow.view.CoordinatorFragment


class SplashFragment : CoordinatorFragment<SplashCoordinator>() {

    private lateinit var rootView: View
    private var splashFlow: SplashCoordinator? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false)
        return rootView
    }

    override fun onFlowBound(flow: SplashCoordinator) {
        Log.d("SplashFragment", "SplashFragment - binding to SplashViewModel")
        splashFlow = flow
        updateView()
    }

    private fun updateView() {
        if (splashFlow!!.stage === SplashCoordinator.Stage.Idle) {
            splashFlow!!.startSplashTimeout()
        }
    }

}
