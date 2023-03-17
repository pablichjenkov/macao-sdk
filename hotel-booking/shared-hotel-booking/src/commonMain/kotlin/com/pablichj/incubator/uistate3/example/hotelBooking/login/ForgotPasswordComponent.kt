package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordComponent(private val authManager: AuthManager) : Component() {

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun requestRecoverPassword(email: String, phone: String) {
        coroutineScope.launch {
            authManager.requestRecoverPassword(email).collect {
                when (val recoverPassReqStatus = it) {
                    is RecoverPasswordRequestStatus.RecoverPasswordFail -> TODO()
                    RecoverPasswordRequestStatus.RecoverPasswordInProgress -> TODO()
                    is RecoverPasswordRequestStatus.RecoverPasswordSuccess -> TODO()
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("ForgotPasswordComponent::Composing()")
        BackPressHandler(
            component = this,
            onBackPressed = { this.handleBackPressed() }
        )
        ForgotPasswordView { email ->
            authManager.requestRecoverPassword(email)
        }
    }

}
