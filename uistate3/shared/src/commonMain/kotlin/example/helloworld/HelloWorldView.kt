package example.helloworld

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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