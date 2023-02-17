package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.IosComponentRender
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackPressHandler
import platform.UIKit.UIViewController

fun ComposeAppViewController(
    appName: String,
    byteArray: ByteArray
): UIViewController = IosComponentRender(
    HelloWorldComponent(),
    appName
)

class HelloWorldComponent : Component() {

    private val helloWorldState = HelloWorldState()

    @Composable
    internal fun Content(modifier: Modifier) {
        BackPressHandler(
            component = this,
            onBackPressed = { handleBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}

@Composable
internal fun HelloWorldView(helloWorldState: HelloWorldState) {

    val userName = helloWorldState.userName

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter your name:")
        OutlinedTextField(
            value = userName,
            onValueChange = {
                helloWorldState.userName = it
            }
        )
        Text("Great to meet you: $userName")
    }
}

class HelloWorldState {
    var userName by mutableStateOf("")
}