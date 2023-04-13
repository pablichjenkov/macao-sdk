package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.platform.LocalSafeAreaInsets

@Composable
internal fun HelloWorldView(helloWorldState: HelloWorldState) {
    val safeAreaInsets = LocalSafeAreaInsets.current
    val userName = helloWorldState.userName

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.fillMaxWidth().height(safeAreaInsets.top.dp))
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