package com.pablichj.incubator.uistate3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.example.helloWorld.HelloWorldNode

/**
 * This class will define the node tree used in your Application.
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
    internal fun PresentContent(
        onBackPressEvent: () -> Unit = {}
    ) {
        onBackPressEventLastUpdate = onBackPressEvent
        // comment it out only for iOS
        HelloWorldNode.Content(Modifier)
    }

}