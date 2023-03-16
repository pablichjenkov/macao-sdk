package com.pablichj.incubator.uistate3.example.hotelBooking.account

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.example.hotelBooking.login.AuthenticationComponent
import com.pablichj.incubator.uistate3.node.Component

class AccountComponent : Component() {

    val authenticationComponent = AuthenticationComponent()

    init {
        authenticationComponent.setParent(this)
    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (authenticationComponent.isUserLogin().not()) {
            authenticationComponent.Content(modifier)
        } else {
            Text("User Account Log-in")
        }

    }
}