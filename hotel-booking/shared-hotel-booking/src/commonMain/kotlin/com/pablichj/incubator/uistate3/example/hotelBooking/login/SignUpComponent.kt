package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backpress.BackPressHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpComponent(private val authManager: AuthManager) : Component() {

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun requestSignUp(email: String, password: String) {
        coroutineScope.launch {
            authManager.requestSignUp(email, password, password).collect {
                when (val signUpReqStatus = it) {
                    is SignUpRequestStatus.SignUpFail -> TODO()
                    SignUpRequestStatus.SignUpInProgress -> TODO()
                    is SignUpRequestStatus.SignUpSuccess -> TODO()
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SignUpComponent::Composing()")
        BackPressHandler(
            component = this,
            onBackPressed = { this.handleBackPressed() }
        )
        SignUpView()
    }

}
