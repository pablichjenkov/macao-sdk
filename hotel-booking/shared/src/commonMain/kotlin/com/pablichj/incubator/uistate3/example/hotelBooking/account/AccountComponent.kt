package com.pablichj.incubator.uistate3.example.hotelBooking.account

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.example.hotelBooking.login.AuthComponent
import com.pablichj.incubator.uistate3.node.Component

class AccountComponent : Component() {

    val authComponent = AuthComponent()

    init {
        authComponent.setParent(this)
    }

    override fun start() {
        super.start()
        authComponent.start()
    }

    override fun stop() {
        super.stop()
        authComponent.stop()
    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (authComponent.isUserLogin().not()) {
            authComponent.Content(modifier)
        } else {
            Text("User Account Log-in")
        }

    }
}