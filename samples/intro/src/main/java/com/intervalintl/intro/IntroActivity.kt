package com.intervalintl.intro

import android.os.Bundle
import com.intervalintl.common.StateContext
import com.intervalintl.onboarding.OnboardindFlow
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.FlowActivity
import com.intervalintl.common.Constants
import com.intervalintl.common.domain.screen.FlowScreenService
import com.intervalintl.common.domain.user.UserService


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

        // Register the Flow Screen Service so Flows can use the Activity View Tree
        stateContext.registerStateService(FlowScreenService(
                supportFragmentManager,
                findViewById(R.id.introActivityViewContainer)))

        // Register a User Service that control the business logic related to the User
        stateContext.registerStateService(UserService())

        return stateContext
    }

}
