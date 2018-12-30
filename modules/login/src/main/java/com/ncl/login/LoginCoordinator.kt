package com.ncl.login

import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.common.domain.user.UserManager
import com.ncl.coordinator.Coordinator
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class LoginCoordinator(coordinatorId: String,
                       private val screenCoordinator: ScreenCoordinator?

) : Coordinator(coordinatorId) {

    enum class Stage {
        Idle,
        LoginOauth,
        LoginInternal
    }

    private var stage: Stage = Stage.Idle
    private var currentCoordinator: Coordinator? = null
    private val viewEventSubject: BehaviorSubject<ViewEvent> = BehaviorSubject.createDefault(ViewEvent.LoginIdle())
    private var userManager: UserManager? = null


    override fun start() {
        if (stage == Stage.Idle) {
            stage = Stage.LoginInternal
            showLoginScreen()
        }
    }

    override fun stop() {

    }

    fun getViewEventPipe() : Observable<ViewEvent> {
        return viewEventSubject
    }

    private fun showLoginScreen() {
        val loginFragment = LoginFragment()
        loginFragment.setCoordinatorId(coordinatorId)

        screenCoordinator?.setView(loginFragment, Constants.LOGIN_FRAGMENT_TAG)
    }

    fun loginButtonClick() {
        //userManager?.loginInternal()

        viewEventSubject.onNext(ViewEvent.ProcessingInternalLogin())


        Observable.create<Any> {
            it.onNext(Any())
        }
                .delay(2, TimeUnit.SECONDS)
                .subscribe {
                    viewEventSubject.onNext(ViewEvent.InternalLoginSuccess())
                }

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


sealed class ViewEvent {

    class LoginIdle : ViewEvent()
    class ProcessingInternalLogin : ViewEvent()
    class InternalLoginSuccess : ViewEvent()
    class InternalLoginFail : ViewEvent()
}
