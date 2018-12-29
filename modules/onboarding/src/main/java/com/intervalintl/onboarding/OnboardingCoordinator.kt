package com.intervalintl.onboarding

import android.os.Handler
import android.os.Looper
import com.intervalintl.common.StateContext
import com.intervalintl.workflow.CompoundCoordinator
import com.intervalintl.common.Constants
import com.intervalintl.login.LoginCoordinator


class OnboardindCoordinator(flowId: String) : CompoundCoordinator<StateContext>(flowId) {


    val mainHandler: Handler by lazy { Handler(Looper.getMainLooper()) }


    override fun onStateContextUpdate(stateContext: StateContext) {

    }

    override fun start() {

        val splashFlow = SplashCoordinator(Constants.SPLASH_FLOW_ID)
        splashFlow.setListener(object : SplashCoordinator.Listener{
            override fun onSplashFinished() {
                mainHandler.post { launchLogin() }
            }
        })

        attachChildFlow(splashFlow)
        splashFlow.start()
    }

    override fun stop() {

    }

    fun launchLogin() {
        val loginFlow = LoginCoordinator(Constants.LOGIN_FLOW_ID)
        attachChildFlow(loginFlow)
        loginFlow.start()
    }
}
