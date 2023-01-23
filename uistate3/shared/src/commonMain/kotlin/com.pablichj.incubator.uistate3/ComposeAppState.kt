package com.pablichj.incubator.uistate3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.Node

/**
 * This class will host the root node passed from the client application.
 * */
class ComposeAppState {

    private lateinit var RootNode: Node
    private var onBackPressEvent: () -> Unit = {}

    fun setRootNode(RootNode: Node) {
        this.RootNode = RootNode
        RootNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            onBackPressEvent()
        }
    }

    fun setBackPressHandler(onBackPressEvent: () -> Unit) {
        this.onBackPressEvent = onBackPressEvent
    }

    fun start() {
        RootNode.start()
    }

    fun stop() {
        RootNode.stop()
    }

    @Composable
    internal fun PresentContent() {
        /**
         * Render the actual node content
         * */
        RootNode.Content(Modifier)
    }

}