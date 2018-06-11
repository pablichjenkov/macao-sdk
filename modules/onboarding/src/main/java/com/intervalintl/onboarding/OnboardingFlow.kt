package com.intervalintl.onboarding

import android.util.Log
import com.intervalintl.common.StateContext
import com.intervalintl.workflow.CompoundFlow
import com.intervalintl.workflow.common.Constants


class OnboardindFlow(flowId: String) : CompoundFlow<StateContext, Nothing>(flowId) {


    override fun onStateContextUpdate(stateContext: StateContext) {

    }

    override fun start() {

        val splashFlow = SplashFlow(Constants.SPLASH_FLOW_ID)
        splashFlow.setListener {
            launchLogin()
        }

        attachChildFlow(splashFlow)
        splashFlow.start()
    }

    override fun stop() {

    }

    fun launchLogin() {
        val loginFlow = SplashFlow(Constants.LOGIN_FLOW_ID)
        loginFlow.setListener {
            Log.d("OnboardindFlow", "Login Success")
        }
    }
}
