package com.intervalintl.intro

import android.os.Bundle
import com.intervalintl.common.StateContext
import com.intervalintl.onboarding.OnboardindFlow
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.FlowActivity
import com.intervalintl.common.Constants
import com.intervalintl.common.FlowViewPortService


class IntroActivity : FlowActivity<StateContext>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    override fun onCreateRootFlow(): Flow<StateContext, *> {
        return OnboardindFlow(Constants.ONBOARDING_FLOW_ID)
    }

    override fun onProvideStateContext(): StateContext {

        val stateContext = StateContext()

        // Register the Flow ViewPort Service
        stateContext.registerState(FlowViewPortService(
                supportFragmentManager,
                findViewById(R.id.introActivityViewContainer)))

        return stateContext
    }

}
