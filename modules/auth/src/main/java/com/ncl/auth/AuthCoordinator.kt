package com.ncl.auth

import com.ncl.common.Constants
import com.ncl.common.domain.auth.*
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.Coordinator
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class AuthCoordinator(coordinatorId: String,
                      private val screenCoordinator: ScreenCoordinator,
                      private val authApi: AuthApi

) : Coordinator(coordinatorId) {

    enum class Stage {
        Idle,
        AuthOptions,
        LoginInternal,
        ProcessingLoginInternal,
        SignupInternal,
        ProcessingSignupInternal,
        Oauth2,
        ProcessingOauth2,
        Reservation,
        ProcessingReservation
    }

    private var stage: Stage = Stage.Idle
    private val loginViewSubject: PublishSubject<LoginViewEvent> = PublishSubject.create()
    private val signupViewSubject: PublishSubject<SignupViewEvent> = PublishSubject.create()
    private val modelEventPipe: PublishSubject<ModelEvent> = PublishSubject.create()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var authOptionsFragment: AuthFragment? = null
    private var loginFragment: LoginFragment? = null
    private var signupFragment: SignupFragment? = null


    override fun start() {
        if (stage == Stage.Idle) {
            stage = Stage.AuthOptions
            showAuthOptionScreen()
        }
    }

    override fun onBackPressed(): Boolean {

        if (stage == Stage.AuthOptions) {
            stage = Stage.Idle
            removeAuthScreen()
            modelEventPipe.onNext(ModelEvent.Cancel())

        } else if (stage == Stage.LoginInternal || stage == Stage.ProcessingLoginInternal) {
            stage = Stage.AuthOptions
            removeLoginScreen()
        }
        else if(stage == Stage.SignupInternal || stage == Stage.ProcessingSignupInternal) {
            stage = Stage.AuthOptions
            removeSignupScreen()
        }

        return true
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    fun getAuthOptionsEventPipe(authOptionsFragment: AuthFragment) {
        this.authOptionsFragment = authOptionsFragment
    }

    fun getLoginViewEventPipe(loginFragment: LoginFragment) : Observable<LoginViewEvent> {

        this.loginFragment = loginFragment

        // fixme: Workaround to send the current state to new subscribers. Use Observable.OnSubscribe
        // fixme: Action instead. Keep a list of subscriber with their id.
        Observable.just(LoginViewEvent.LoginIdle())
                .delay(10, TimeUnit.MILLISECONDS)
                .subscribe {
                    loginViewSubject.onNext(it)
                }


        return loginViewSubject
    }

    fun getSignupViewEventPipe(signupFragment: SignupFragment) : Observable<SignupViewEvent> {

        this.signupFragment = signupFragment

        // fixme: Workaround to send the current state to new subscribers. Use Observable.OnSubscribe
        // fixme: Action instead. Keep a list of subscriber with their id.
        Observable.just(SignupViewEvent.SignupIdle())
                .delay(10, TimeUnit.MILLISECONDS)
                .subscribe {
                    signupViewSubject.onNext(it)
                }

        return signupViewSubject
    }

    fun getModelEventPipe() : Observable<ModelEvent> {
        return modelEventPipe
    }

    fun startLoginFlow() {
        if (stage == Stage.AuthOptions) {
            stage = Stage.LoginInternal

            //removeAuthScreen()

            showLoginScreen()
        }
    }

    fun startSignupFlow() {

        if (stage == Stage.AuthOptions) {
            stage = Stage.SignupInternal

            //removeAuthScreen()

            showSignupScreen()
        }

    }

    fun startOAuth2Flow() {

        if (stage == Stage.AuthOptions) {
            //stage = Stage.Oauth2

            //removeAuthScreen()

//            showLoginScreen()
        }

    }

    fun startReservationLoginFlow() {

        if (stage == Stage.AuthOptions) {
            //stage = Stage.Reservation

            //removeAuthScreen()

//            showLoginScreen()
        }

    }

    fun loginButtonClick(loginForm: LoginFormData) {

        if (stage != Stage.LoginInternal) {
            // Only proceed if we are in the Login Internal stage, otherwise return here

            return
        }

        stage = Stage.ProcessingLoginInternal
        loginViewSubject.onNext(LoginViewEvent.ProcessingInternalLogin())

        authApi.loginInternal(loginForm).subscribe(object : Observer<LoginFormResp> {

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onNext(event: LoginFormResp) {
                stage = Stage.LoginInternal

                if (!event.error) {
                    loginViewSubject.onNext(LoginViewEvent.InternalLoginSuccess())

                } else {
                    loginViewSubject.onNext(LoginViewEvent.InternalLoginFail())
                }

            }

            override fun onError(e: Throwable) {
                stage = Stage.LoginInternal
                loginViewSubject.onNext(LoginViewEvent.InternalLoginFail())
            }

            override fun onComplete() {}

        })

    }

    fun signupButtonClick(signUpFormData: SignUpFormData) {

        if (stage != Stage.SignupInternal) {
            // Only proceed if we are in the Login Internal stage, otherwise return here

            return
        }

        stage = Stage.ProcessingSignupInternal
        //viewEventSubject.onNext(ViewEvent.ProcessingSignupInternal())

        authApi.signUpInternal(signUpFormData).subscribe(object : Observer<SignUpFormResp> {

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onNext(event: SignUpFormResp) {

                if (!event.error) {
                    // TODO: separate event pipes by auth type, one per each
                    //viewEventSubject.onNext(ViewEvent.InternalLoginSuccess())

                } else {
                    //viewEventSubject.onNext(ViewEvent.InternalLoginFail())
                }

            }

            override fun onError(e: Throwable) {
                //viewEventSubject.onNext(ViewEvent.InternalLoginFail())
            }

            override fun onComplete() {}

        })

    }

    private fun showAuthOptionScreen() {
        authOptionsFragment = AuthFragment()

        authOptionsFragment?.run {
            setCoordinatorId(coordinatorId)
            screenCoordinator.push(this, Constants.AUTH_FRAGMENT_TAG)
        }

    }

    private fun removeAuthScreen() {

        authOptionsFragment?.run {
            screenCoordinator.pop(this)
        }

        authOptionsFragment = null
    }

    private fun showLoginScreen() {
        loginFragment = LoginFragment()

        loginFragment?.run {
            setCoordinatorId(coordinatorId)
            screenCoordinator.push(this, Constants.LOGIN_FRAGMENT_TAG)
        }

    }

    private fun removeLoginScreen() {

        loginFragment?.run {
            screenCoordinator.pop(this)
        }

        loginFragment = null
    }

    private fun showSignupScreen() {
        signupFragment = SignupFragment()

        signupFragment?.run {
            setCoordinatorId(coordinatorId)
            screenCoordinator.push(this, Constants.SIGNUP_FRAGMENT_TAG)
        }
    }

    private fun removeSignupScreen() {

        signupFragment?.run {
            screenCoordinator.pop(this)
        }

        signupFragment = null
    }

}


sealed class LoginViewEvent {
    class LoginIdle : LoginViewEvent()
    class ProcessingInternalLogin : LoginViewEvent()
    class InternalLoginSuccess : LoginViewEvent()
    class InternalLoginFail : LoginViewEvent()
}

sealed class SignupViewEvent {
    class SignupIdle : SignupViewEvent()
    class ProcessingInternalSignup : SignupViewEvent()
    class InternalSignupSuccess : SignupViewEvent()
    class InternalSignupFail : SignupViewEvent()
}

sealed class ModelEvent {
    class Cancel : ModelEvent()
    class Complete : ModelEvent()
}