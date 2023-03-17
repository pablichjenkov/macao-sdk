package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

class SignInComponent(private val authManager: AuthManager) : Component() {
    val outFlow = MutableSharedFlow<Out>()
    private val showProgressIndicator = mutableStateOf(false)
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun requestSignIn(email: String, password: String) {
        coroutineScope.launch {
            authManager.requestSignIn(
                email = email,
                password = password
            ).collect {
                when (val logInReqStatus = it) {
                    is SignInRequestStatus.SignInFail -> {
                        showProgressIndicator.value = false
                        outFlow.emit(Out.LoginFail)
                    }
                    SignInRequestStatus.SignInInProgress -> {
                        showProgressIndicator.value = true
                    }
                    is SignInRequestStatus.SignInSuccess -> {
                        showProgressIndicator.value = false
                        outFlow.emit(Out.LoginSuccess)
                    }
                }
            }
        }.invokeOnCompletion {
            if (it != null) {
                println("Pablo:: SignInComponent_Coroutine has been cancelled")
            }
        }
    }

    override fun stop() {
        super.stop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SignInComponent::Composing()")
        Box {
            BackPressHandler(
                component = this@SignInComponent,
                onBackPressed = { this@SignInComponent.handleBackPressed() }
            )
            //SignInView({},{},{})
            SignInView2(
                { coroutineScope.launch { outFlow.emit(Out.SignUpClick) } },
                { email, password ->
                    requestSignIn(email, password)
                },
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
