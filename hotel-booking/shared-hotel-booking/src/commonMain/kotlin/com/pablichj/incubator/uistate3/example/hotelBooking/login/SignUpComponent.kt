package com.pablichj.incubator.uistate3.example.hotelBooking.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler

class SignUpComponent(private val authenticationManager: AuthenticationManager) : Component() {

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
