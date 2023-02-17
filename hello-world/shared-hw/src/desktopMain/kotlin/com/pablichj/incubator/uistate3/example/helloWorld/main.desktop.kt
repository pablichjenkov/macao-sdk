package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.pablichj.incubator.uistate3.DesktopNodeRender
import example.helloworld.HelloWorldNode
import kotlin.system.exitProcess

@Composable
fun HelloWorldApp() {
    MaterialTheme {
        DesktopNodeRender(
            rootNode = HelloWorldNode(),
            onBackPressEvent = {
                exitProcess(0)
            }
        )
    }
}