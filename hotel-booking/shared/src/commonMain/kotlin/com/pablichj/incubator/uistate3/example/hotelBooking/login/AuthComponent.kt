package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.pablichj.incubator.uistate3.example.hotelBooking.AuthorizeAPI
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.stack.StackBarItem
import com.pablichj.incubator.uistate3.node.stack.StackComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthComponent : StackComponent() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val authManager: AuthManager = DefaultAuthManager(AuthorizeAPI())
    private val signInComponent = SignInComponent(authManager)
    private val signUpComponent = SignUpComponent(authManager)
    private val forgotPasswordComponent = ForgotPasswordComponent(authManager)

    init {
        signUpComponent.setParent(this)
        signInComponent.setParent(this)
        forgotPasswordComponent.setParent(this)

        coroutineScope.launch {
            signInComponent.outFlow.collect {
                when (it) {
                    SignInComponent.Out.SignUpClick -> {
                        backStack.push(signUpComponent)
                    }
                    SignInComponent.Out.ForgotPasswordClick -> {
                        backStack.push(forgotPasswordComponent)
                    }
                    SignInComponent.Out.LoginFail -> {
                        println("Pablo:: Login Fail")
                    }
                    SignInComponent.Out.LoginSuccess -> {
                        println("Pablo:: Login Success")
                    }
                }
            }
        }.invokeOnCompletion {
            if (it != null) {
                println("Pablo:: AuthComponent_Coroutine has been cancelled")
            }
        }

        if (isUserLogin().not()) {
            backStack.push(signInComponent)
        }
    }

    override fun start() {
        super.start()
        activeComponent.value?.start()
    }

    override fun stop() {
        super.stop()
        activeComponent.value?.stop()
    }

    fun isUserLogin(): Boolean {
        return authManager.getCurrentToken().isNullOrEmpty().not()
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem {
        val selectedNavItem = if (component == signInComponent) {
            StackBarItem(
                label = "Sign In",
                icon = Icons.Default.Home,
            )
        } else {
            StackBarItem(
                label = "Search",
                icon = Icons.Default.Search,
            )
        }
        return selectedNavItem
    }

}
