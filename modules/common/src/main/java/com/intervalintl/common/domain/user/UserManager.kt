package com.intervalintl.common.domain.user

import android.app.Activity
import android.content.Intent


interface UserManager {


    // region: Internal Auth

    val token: String

    fun setListener(listener: Listener)

    fun loginInternal(loginFormData: LoginFormData)

    fun signUpInternal(formData: SignUpFormData)

    // endregion


    // region: Oauth2 Auth

    fun loginPersistedOauthUser()

    fun launchAuthActivity(activity: Activity)

    fun handleAuthActivityResult(resultCode: Int, data: Intent)

    // endregion


    interface Listener {
        // Internal Events
        fun internalSignUpFail()

        fun internalSignUpSuccess()
        fun internalLoginFail()
        fun internalLoginSuccess()

        // Oauth Events
        fun oauthProviderPlatformLoginFail()

        fun oauthProviderPlatformVerifyEmailSent()
        fun oauthHamperPlatformLoginStarted()
        fun oauthHamperPlatformLoginFail(t: Throwable)
        fun oauthHamperPlatformLoginSuccess()
    }

    companion object {

        val AUTHENTICATION_SCHEME = "Bearer "
        val HEADER_AUTHORIZATION = "Authorization"
    }

}
