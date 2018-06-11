package com.intervalintl.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intervalintl.workflow.view.FlowFragment


class SplashFragment : FlowFragment<SplashFlow>() {

    private lateinit var rootView: View
    private var splashFlow: SplashFlow? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false)
        return rootView
    }

    override fun onFlowBound(flow: SplashFlow) {
        Log.d("SplashFragment", "SplashFragment - binding to SplashViewModel")
        splashFlow = flow
        updateView()
    }

    private fun updateView() {
        if (splashFlow!!.stage === SplashFlow.Stage.Idle) {
            splashFlow!!.startSplashTimeout()
        }
    }

}
