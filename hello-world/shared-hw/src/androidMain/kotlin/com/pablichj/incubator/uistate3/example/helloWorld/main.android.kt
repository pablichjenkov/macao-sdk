package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import com.pablichj.incubator.uistate3.AndroidNodeRender
import example.helloworld.HelloWorldNode

@Composable
fun HelloWorldApp(componentActivity: ComponentActivity) {
    AndroidNodeRender(
        rootNode = HelloWorldNode(),
        onBackPressEvent = { componentActivity.finishAffinity() }
    )
}