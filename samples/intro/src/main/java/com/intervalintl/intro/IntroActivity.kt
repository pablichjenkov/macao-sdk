package com.intervalintl.intro

import android.os.Bundle
import com.intervalintl.common.StateContext
import com.intervalintl.onboarding.OnboardindCoordinator
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.CoordinatorActivity
import com.intervalintl.common.Constants
import com.intervalintl.common.domain.screen.ScreenCoordinatorService
import com.intervalintl.common.domain.user.UserService


class IntroActivity : CoordinatorActivity<StateContext>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

    override fun onCreateRootFlow(): Coordinator<StateContext> {
        return OnboardindCoordinator(Constants.ONBOARDING_FLOW_ID)
    }

    override fun onProvideStateContext(): StateContext {

        val stateContext = StateContext()

        // Register the Coordinator Screen Service so Flows can use the Activity View Tree
        stateContext.registerStateService(ScreenCoordinatorService(
                supportFragmentManager,
                findViewById(R.id.introActivityViewContainer)))

        // Register a User Service that control the business logic related to the User
        stateContext.registerStateService(UserService())

        return stateContext
    }

}
