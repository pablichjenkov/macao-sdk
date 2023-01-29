package example.helloworld

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HelloWorldState {
    var userName by mutableStateOf("")
}