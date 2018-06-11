package com.intervalintl.login

import com.intervalintl.common.StateContext
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.common.Constants
import com.intervalintl.workflow.common.FlowViewPortService
import com.intervalintl.workflow.view.FlowViewPort
import io.reactivex.subjects.BehaviorSubject


class LoginFlow(flowId: String) : Flow<StateContext, LoginFlowEvent>(flowId) {

    enum class Stage {
        Idle,
        LoginOauth,
        LoginInternal
    }

    private var stage: Stage = Stage.Idle
    private var onStageChildFlow: Flow<StateContext, LoginFlowEvent>? = null
    private val loginSubject: BehaviorSubject<out LoginFlowEvent> = BehaviorSubject.createDefault(LoginFlowEvent.LoginIdle())
    private var flowViewPort: FlowViewPort? = null


    override fun onStateContextUpdate(stateContext: StateContext) {
        val clazz: Class<FlowViewPortService> = FlowViewPortService::class.java
        val viewPortService: FlowViewPortService? = stateContext.getState(clazz, Constants.DEFAULT_FLOW_VIEWPORT_SERVICE_ID)
        flowViewPort = viewPortService?.getFlowViewPort()
    }

    override fun start() {
        if (stage == Stage.Idle) {
            stage = Stage.LoginInternal
            //onStageCoordinator = getChildById(Constants.LOGIN_VIEWMODEL_ID)
            //onStageCoordinator.start()
            showLoginScreen()
        }
    }

    override fun stop() {

    }

    private fun showLoginScreen() {
        val loginFragment = LoginFragment()
        loginFragment.setFlowId(flowId)

        flowViewPort?.setView(loginFragment, Constants.LOGIN_FRAGMENT_TAG)
    }

}


data class LoginInput(val username: String, val password: String)


sealed class LoginFlowEvent {

    class LoginIdle : LoginFlowEvent() {}

}
