package com.intervalintl.workflow

import android.os.Bundle
import com.intervalintl.workflow.common.Constants
import com.intervalintl.workflow.common.FlowViewPortService


class MainActivity : FlowActivity<StateContext>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateRootFlow(): Flow<StateContext, *> {
        return LoginFlow(Constants.LOGIN_FLOW_ID)
    }

    override fun onProvideStateContext(): StateContext {

        val stateContext = StateContext()

        // Register the Flow ViewPort Service
        stateContext.registerState(FlowViewPortService(
                supportFragmentManager,
                findViewById(R.id.loginActivityViewContainer)))

        return stateContext
    }

}
