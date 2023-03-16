package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.pablichj.incubator.uistate3.example.hotelBooking.AuthorizeAPI
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.topbar.StackBarItem
import com.pablichj.incubator.uistate3.node.topbar.StackComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationComponent : StackComponent() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val authenticationManager = AuthenticationManager(AuthorizeAPI())
    private val signInComponent = SignInComponent(authenticationManager)
    private val signUpComponent = SignUpComponent(authenticationManager)

    init {
        signInComponent.setParent(this)
        coroutineScope.launch {
            signInComponent.outFlow.collect {
                when (it) {
                    SignInComponent.Out.SignUpClick -> {
                        backStack.push(signUpComponent)
                    }
                    SignInComponent.Out.ForgotPasswordClick -> {
                        //todo: push password component
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
                println("Pablo:: Coroutine has been cancelled")
            }
        }

        signUpComponent.setParent(this)

        if (isUserLogin().not()) {
            backStack.push(signInComponent)
        }
    }

    fun isUserLogin(): Boolean {
        return authenticationManager.getCurrentToken().isNullOrEmpty().not()
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
