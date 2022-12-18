package com.pablichj.encubator.node.example

import androidx.compose.ui.window.WindowState
import com.pablichj.encubator.node.JvmWindowSizeInfoProvider
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext

abstract class WindowNode(parentContext: NodeContext): Node(parentContext) {
    protected val windowState = WindowState()
    protected val windowSizeInfoProvider = JvmWindowSizeInfoProvider(windowState)
}