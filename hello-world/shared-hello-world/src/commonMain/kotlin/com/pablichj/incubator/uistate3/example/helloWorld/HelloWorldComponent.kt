package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backpress.BackPressHandler

class HelloWorldComponent : Component() {

    private val helloWorldState = HelloWorldState()

    @Composable
    /*internal*/ override fun Content(modifier: Modifier) {
        BackPressHandler(
            component = this,
            onBackPressed = { handleBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}