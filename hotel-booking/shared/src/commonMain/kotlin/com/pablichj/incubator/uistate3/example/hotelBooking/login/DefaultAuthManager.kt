package com.pablichj.incubator.uistate3.example.hotelBooking.login

import com.pablichj.incubator.uistate3.example.hotelBooking.AuthorizeAPI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DefaultAuthManager(authorizeAPI: AuthorizeAPI) : AuthManager {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var token: String? = null

    override fun requestSignUp(email: String, password1: String, password2: String): StateFlow<SignUpRequestStatus> {
        val signUpRequestStatus = MutableStateFlow<SignUpRequestStatus>(SignUpRequestStatus.SignUpInProgress)
        coroutineScope.launch {
            delay(3000)
            signUpRequestStatus.value = SignUpRequestStatus.SignUpSuccess()
            cancel(null)
        }
        return signUpRequestStatus
    }

    override fun requestSignIn(email: String, password: String): StateFlow<SignInRequestStatus> {
        val signInRequestStatus = MutableStateFlow<SignInRequestStatus>(SignInRequestStatus.SignInInProgress)
        coroutineScope.launch {
            delay(3000)
            token = "fake_token"
            signInRequestStatus.value = SignInRequestStatus.SignInSuccess()
            cancel(null)
        }
        return signInRequestStatus
    }

    override fun requestRecoverPassword(
        email: String
    ): StateFlow<RecoverPasswordRequestStatus> {
        val recoverPasswordRequestStatus = MutableStateFlow<RecoverPasswordRequestStatus>(RecoverPasswordRequestStatus.RecoverPasswordInProgress)
        coroutineScope.launch {
            delay(3000)
            token = "fake_token"
            recoverPasswordRequestStatus.value = RecoverPasswordRequestStatus.RecoverPasswordSuccess()
            cancel(null)
        }
        return recoverPasswordRequestStatus
    }

    override fun getCurrentToken(): String? {
        return token
    }

}

sealed class SignUpRequestStatus {
    object SignUpInProgress: SignUpRequestStatus()
    class SignUpSuccess: SignUpRequestStatus()
    class SignUpFail: SignUpRequestStatus()
}

sealed class SignInRequestStatus {
    object SignInInProgress: SignInRequestStatus()
    class SignInSuccess: SignInRequestStatus()
    class SignInFail: SignInRequestStatus()
}

sealed class RecoverPasswordRequestStatus {
    object RecoverPasswordInProgress: RecoverPasswordRequestStatus()
    class RecoverPasswordSuccess: RecoverPasswordRequestStatus()
    class RecoverPasswordFail: RecoverPasswordRequestStatus()
}