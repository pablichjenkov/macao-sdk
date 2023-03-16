package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SignInComponent(private val authenticationManager: AuthenticationManager) : Component() {
    val outFlow = MutableSharedFlow<Out>()
    private val showProgressIndicator = mutableStateOf(false)
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun requestLogin() {
        coroutineScope.launch {
            authenticationManager.loginRequestStatus.collect {
                when (val loginReqStatus = it) {
                    is LoginRequestStatus.LoginFail -> {
                        showProgressIndicator.value = false
                        outFlow.emit(Out.LoginFail)
                        //this.cancel()
                    }
                    LoginRequestStatus.LoginInProgress -> {
                        showProgressIndicator.value = true
                    }
                    is LoginRequestStatus.LoginSuccess -> {
                        showProgressIndicator.value = false
                        outFlow.emit(Out.LoginSuccess)
                        //this.cancel()
                    }
                    null -> {}
                }
            }
        }.invokeOnCompletion {
            if (it != null) {
                println("Pablo:: Coroutine has been cancelled")
            }
        }

        authenticationManager.requestLogin("fakeuser", "fakepassword")
    }

    override fun start() {
        super.start()
        coroutineScope = CoroutineScope(Dispatchers.Main)
    }

    override fun stop() {
        super.stop()
        coroutineScope.cancel()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SignInComponent::Composing()")
        Box {
            BackPressHandler(
                component = this@SignInComponent,
                onBackPressed = { this@SignInComponent.handleBackPressed() }
            )
            //SignInView(loginManager)
            SignInView2(
                { coroutineScope.launch { outFlow.emit(Out.SignUpClick) } },
                { requestLogin() },
                { coroutineScope.launch { outFlow.emit(Out.ForgotPasswordClick) } }
            )
            if (showProgressIndicator.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    sealed class Out {
        object SignUpClick : Out()
        object ForgotPasswordClick : Out()
        object LoginSuccess : Out()
        object LoginFail : Out()
    }
}
