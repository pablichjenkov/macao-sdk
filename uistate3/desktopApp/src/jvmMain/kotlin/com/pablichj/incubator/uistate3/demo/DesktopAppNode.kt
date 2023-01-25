package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier

class DesktopAppNode : WindowNode {
    private val activeWindows = mutableStateListOf<WindowNode>()

    private val MainWindowNode = MainWindowNode(
        onOpenDeepLinkClick = {
            openDeepLinkWindow()
        },
        onRootNodeSelection = {
            openWindow(it)
        },
        onExitClick = { exit() }
    )

    private val DeepLinkDemoNode = DeepLinkDemoNode(
        onDeepLinkClick = { path ->
            MainWindowNode.handleDeepLink(path)
        },
        onCloseClick = {
            closeDeepLinkWindow()
        }
    )

    private val DrawerWindowNode = DrawerWindowNode(
        onCloseClick = {
            closeDrawerWindowNode()
        }
    )

    private val NavBarWindowNode = NavBarWindowNode(
        onCloseClick = {
            closeNavBarWindowNode()
        }
    )

    private val PanelWindowNode = PanelWindowNode(
        onCloseClick = {
            closePanelWindowNode()
        }
    )

    private val FullAppWindowNode = FullAppWindowNode(
        onCloseClick = {
            closeFullAppWindowNode()
        }
    )

    init {
        activeWindows.add(MainWindowNode)
    }

    private fun openDeepLinkWindow() {
        if (!activeWindows.contains(DeepLinkDemoNode)) {
            activeWindows.add(DeepLinkDemoNode)
        }
    }

    private fun openWindow(windowNodeSample: WindowNodeSample) {
        val window = when (windowNodeSample) {
            WindowNodeSample.Drawer -> DrawerWindowNode
            WindowNodeSample.Navbar -> NavBarWindowNode
            WindowNodeSample.Panel -> PanelWindowNode
            WindowNodeSample.FullApp -> FullAppWindowNode
        }
        if (!activeWindows.contains(window)) {
            activeWindows.add(window)
        }
    }

    private fun closeDeepLinkWindow() {
        activeWindows.remove(DeepLinkDemoNode)
    }

    private fun closeDrawerWindowNode() {
        activeWindows.remove(DrawerWindowNode)
    }

    private fun closeNavBarWindowNode() {
        activeWindows.remove(NavBarWindowNode)
    }

    private fun closePanelWindowNode() {
        activeWindows.remove(PanelWindowNode)
    }

    private fun closeFullAppWindowNode() {
        activeWindows.remove(FullAppWindowNode)
    }

    private fun exit() {
        activeWindows.clear()
    }

    @Composable
    override fun WindowContent(modifier: Modifier) {
        for (window in activeWindows) {
            key(window) {
                window.WindowContent(modifier)
            }
        }
    }

}