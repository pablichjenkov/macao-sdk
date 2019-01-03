package com.ncl.common.domain.auth

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable


interface AuthApi {

    companion object {
        val AUTHENTICATION_SCHEME = "Bearer "
        val HEADER_AUTHORIZATION = "Authorization"
    }


    fun getAuthToken(): Observable<String>


    // region: Internal Auth

    fun loginInternal(loginFormData: LoginFormData): Observable<LoginFormResp>

    fun signUpInternal(signupFormData: SignUpFormData): Observable<SignUpFormResp>

    // endregion


    // region: Oauth2 Auth

    fun loginPersistedOauthUser(): Observable<String>

    fun launchAuthActivity(currentActivity: Activity)

    fun handleAuthActivityResult(resultCode: Int, data: Intent)

    // endregion

}
