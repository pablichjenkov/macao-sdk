package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HelloWorldState {
    var userName by mutableStateOf("")
}