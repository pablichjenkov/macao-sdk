package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.backpress.BackPressHandler

class HelloWorldComponent : Component() {

    private val helloWorldState = HelloWorldState()

    @Composable
    override fun Content(modifier: Modifier) {
        BackPressHandler(
            component = this,
            onBackPressed = { handleBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}