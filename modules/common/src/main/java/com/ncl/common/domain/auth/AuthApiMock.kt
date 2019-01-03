package com.ncl.common.domain.auth

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class AuthApiMock : AuthApi {

    override fun getAuthToken(): Observable<String> {

        return Observable.just("Mock_Auth_Token")
                .delay(100, TimeUnit.MILLISECONDS)

    }

    override fun loginInternal(loginFormData: LoginFormData): Observable<LoginFormResp> {

        val loginFormResp = LoginFormResp(false,
                0,
                null,
                "Mock_Auth_Token")

        return Observable.just(loginFormResp)
                .delay(2, TimeUnit.SECONDS)

    }

    override fun signUpInternal(signupFormData: SignUpFormData): Observable<SignUpFormResp> {

        val signUpFormResp = SignUpFormResp(false,
                0,
                null,
                true)

        return Observable.just(signUpFormResp)
                .delay(2, TimeUnit.SECONDS)

    }

    override fun loginPersistedOauthUser(): Observable<String> {

        return Observable.just("Mock_OAuth2_Token")
                .delay(2, TimeUnit.SECONDS)

    }

    override fun launchAuthActivity(currentActivity: Activity) {
        // TODO: Launch a fake Activity here
    }

    override fun handleAuthActivityResult(resultCode: Int, data: Intent) {
        // TODO: Handle the result of the fake Activity here
    }

}