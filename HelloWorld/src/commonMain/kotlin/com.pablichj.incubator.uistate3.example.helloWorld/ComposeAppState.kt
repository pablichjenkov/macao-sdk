package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.Node

/**
 * The
 * */
class ComposeAppState {

    private val HelloWorldNode: Node = HelloWorldNode()
    private var onBackPressEventLastUpdate: () -> Unit = {}

    init {
        HelloWorldNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            onBackPressEventLastUpdate()
        }
    }

    fun start() {
        HelloWorldNode.start()
    }

    fun stop() {}

    @Composable
    fun PresentContent(
        onBackPressEvent: () -> Unit = {}
    ) {
        onBackPressEventLastUpdate = onBackPressEvent
        HelloWorldNode.Content(Modifier)
    }

}