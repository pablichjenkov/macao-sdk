package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.pablichj.incubator.uistate3.BrowserNodeRender
import example.helloworld.HelloWorldNode

@Composable
fun HelloWorldApp() {
    MaterialTheme {
        BrowserNodeRender(
            rootNode = HelloWorldNode(),
            onBackPressEvent = {
                println("Back pressed event reached root node. Should ste back button invisible")
            }
        )
    }
}


