package com.intervalintl.login

import com.intervalintl.common.StateContext
import com.intervalintl.workflow.Coordinator
import com.intervalintl.common.Constants
import com.intervalintl.common.domain.screen.ScreenCoordinatorService
import com.intervalintl.common.domain.user.UserManager
import com.intervalintl.common.domain.user.UserService
import com.intervalintl.workflow.view.ScreenCoordinator
import io.reactivex.subjects.BehaviorSubject


class LoginCoordinator(flowId: String) : Coordinator<StateContext>(flowId) {

    enum class Stage {
        Idle,
        LoginOauth,
        LoginInternal
    }

    private var stage: Stage = Stage.Idle
    private var currentCoordinator: Coordinator<StateContext>? = null
    private val loginSubject: BehaviorSubject<out LoginFlowEvent> = BehaviorSubject.createDefault(LoginFlowEvent.LoginIdle())
    private var screenCoordinator: ScreenCoordinator? = null
    private var userManager: UserManager? = null


    override fun onStateContextUpdate(stateContext: StateContext) {

        val screenCoordinatorService: ScreenCoordinatorService? = stateContext.getStateService(ScreenCoordinatorService::class.java,
                Constants.DEFAULT_SCREEN_SERVICE_ID)

        screenCoordinator = screenCoordinatorService?.getFlowViewPort()


        val userService: UserService? = stateContext.getStateService(UserService::class.java,
                Constants.DEFAULT_USER_MANAGER_SERVICE_ID)

        userManager = userService?.getUserManager()

        userManager?.setListener(userManagerListener)
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


    fun subscribe() {

    }

    private fun showLoginScreen() {
        val loginFragment = LoginFragment()
        loginFragment.setFlowId(flowId)

        screenCoordinator?.setView(loginFragment, Constants.LOGIN_FRAGMENT_TAG)
    }


    var userManagerListener = object: UserManager.Listener {

        override fun internalSignUpFail() {
            //screenCoordinator?.setView()
        }

        override fun internalSignUpSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun internalLoginFail() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun internalLoginSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun oauthProviderPlatformLoginFail() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun oauthProviderPlatformVerifyEmailSent() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun oauthHamperPlatformLoginStarted() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun oauthHamperPlatformLoginFail(t: Throwable) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun oauthHamperPlatformLoginSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}


data class LoginInput(val username: String, val password: String)


sealed class LoginFlowEvent {

    class LoginIdle : LoginFlowEvent() {}

}
