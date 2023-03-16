package com.pablichj.incubator.uistate3.example.hotelBooking.login

import com.pablichj.incubator.uistate3.example.hotelBooking.AuthorizeAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthenticationManager(authorizeAPI: AuthorizeAPI) {

    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val loginRequestStatus = MutableStateFlow<LoginRequestStatus?>(null)

    private var token: String? = null

    fun getCurrentToken(): String? {
        return token
    }

    fun requestLogin(user: String, password: String) {

        coroutineScope.launch {
            loginRequestStatus.value = LoginRequestStatus.LoginInProgress
            delay(3000)
            token = "fake_token"
            loginRequestStatus.value = LoginRequestStatus.LoginSuccess()
        }

    }

    fun signup(user: String, password: String) {

    }

}


sealed class LoginRequestStatus {
    object LoginInProgress: LoginRequestStatus()
    class LoginSuccess: LoginRequestStatus()
    class LoginFail: LoginRequestStatus()
}