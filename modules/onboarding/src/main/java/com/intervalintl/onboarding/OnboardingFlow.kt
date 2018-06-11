package com.intervalintl.onboarding

import android.os.Handler
import android.os.Looper
import com.intervalintl.common.StateContext
import com.intervalintl.workflow.CompoundFlow
import com.intervalintl.common.Constants
import com.intervalintl.login.LoginFlow


class OnboardindFlow(flowId: String) : CompoundFlow<StateContext, Nothing>(flowId) {


    val mainHandler: Handler by lazy { Handler(Looper.getMainLooper()) }


    override fun onStateContextUpdate(stateContext: StateContext) {

    }

    override fun start() {

        val splashFlow = SplashFlow(Constants.SPLASH_FLOW_ID)
        splashFlow.setListener(object : SplashFlow.Listener{
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
        val loginFlow = LoginFlow(Constants.LOGIN_FLOW_ID)
        attachChildFlow(loginFlow)
        loginFlow.start()
    }
}
