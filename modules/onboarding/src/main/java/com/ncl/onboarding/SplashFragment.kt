package com.ncl.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ncl.coordinator.view.CoordinatorFragment


class SplashFragment : CoordinatorFragment<SplashCoordinator>() {

    private lateinit var rootView: View
    private var splashCoordinator: SplashCoordinator? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false)
        return rootView
    }

    override fun onCoordinatorBound(coordinator: SplashCoordinator) {
        Log.d("SplashFragment", "SplashFragment - binding to SplashViewModel")
        splashCoordinator = coordinator
        updateView()
    }

    private fun updateView() {
        if (splashCoordinator?.stage == SplashCoordinator.Stage.Idle) {
            splashCoordinator?.startSplashTimeout()
        }
    }

}
