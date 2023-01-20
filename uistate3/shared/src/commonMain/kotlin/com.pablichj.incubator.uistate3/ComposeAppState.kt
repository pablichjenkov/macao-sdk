package com.pablichj.incubator.uistate3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.Node

/**
 * This class will define the node tree used in your Application.
 * */
class ComposeAppState {

    private lateinit var RootNode: Node
    private var onBackPressEventLastUpdate: () -> Unit = {}

    fun setRootNode(RootNode: Node) {
        this.RootNode = RootNode
        RootNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            println("Pablo, exitProcess isnt working")
            onBackPressEventLastUpdate()
        }
    }

    fun start() {
        RootNode.start()
    }

    fun stop() {}

    @Composable
    internal fun PresentContent(
        onBackPressEvent: () -> Unit = {}
    ) {
        onBackPressEventLastUpdate = onBackPressEvent
        // comment it out only for iOS
        RootNode.Content(Modifier)
    }

}