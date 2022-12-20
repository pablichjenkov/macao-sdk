package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext

class DesktopAppNode(
    parentContext: NodeContext
) : Node(parentContext) {
    private val activeWindows = mutableStateListOf<Node>()

    private val MainWindowNode = MainWindowNode(
        parentContext = context,
        onOpenDeepLinkClick = {
            openDeepLinkWindow()
        },
        onExitClick = { exit() }
    )

    private val DeepLinkNode = DeepLinkNode(
        context,
        onDeepLinkClick = { path ->
            MainWindowNode.handleDeepLink(path)
        },
        onCloseClick = {
            closeDeepLinkWindow()
        }
    )

    init {
        activeWindows.add(MainWindowNode)
    }

    private fun openDeepLinkWindow() {
        if (!activeWindows.contains(DeepLinkNode)) {
            activeWindows.add(DeepLinkNode)
        }
    }

    private fun closeDeepLinkWindow() {
        activeWindows.remove(DeepLinkNode)
    }

    private fun exit() {
        activeWindows.clear()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        for (window in activeWindows) {
            key(window) {
                window.Content(modifier)
            }
        }
    }

}