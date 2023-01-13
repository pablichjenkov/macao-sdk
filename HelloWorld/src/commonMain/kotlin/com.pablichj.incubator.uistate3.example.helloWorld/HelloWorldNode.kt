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
import com.pablichj.incubator.uistate3.node.BackPressHandler
import com.pablichj.incubator.uistate3.node.Node

class HelloWorldNode : Node() {

    private val helloWorldState = HelloWorldState()

    @Composable
    override fun Content(modifier: Modifier) {
        BackPressHandler { backPressedCallbackHandler.onBackPressed() }
        HelloWorldView(helloWorldState)
    }

}

@Composable
private fun HelloWorldView(helloWorldState: HelloWorldState) {

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

private class HelloWorldState {
    var userName by mutableStateOf("")
}