package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component

class DesktopAppComponent : Component() {
    private val activeComponents = mutableStateListOf<Component>()

    private val MainWindowNode = MainWindowComponent(
        onOpenDeepLinkClick = {
            openDeepLinkWindow()
        },
        onRootNodeSelection = {
            openWindow(it)
        },
        onExitClick = { exit() }
    )

    private val DeepLinkDemoNode = DeepLinkDemoComponent(
        onDeepLinkClick = { path ->
            MainWindowNode.handleDeepLink(path)
        },
        onCloseClick = {
            closeDeepLinkWindow()
        }
    )

    private val DrawerWindowNode = DrawerWindowComponent(
        onCloseClick = {
            closeDrawerWindowNode()
        }
    )

    private val NavBarWindowNode = NavBarWindowComponent(
        onCloseClick = {
            closeNavBarWindowNode()
        }
    )

    private val PanelWindowNode = PanelWindowComponent(
        onCloseClick = {
            closePanelWindowNode()
        }
    )

    private val FullAppWindowNode = FullAppWindowComponent(
        onCloseClick = {
            closeFullAppWindowNode()
        }
    )

    init {
        activeComponents.add(MainWindowNode)
    }

    private fun openDeepLinkWindow() {
        if (!activeComponents.contains(DeepLinkDemoNode)) {
            activeComponents.add(DeepLinkDemoNode)
        }
    }

    private fun openWindow(windowSample: WindowSample) {
        val window = when (windowSample) {
            WindowSample.Drawer -> DrawerWindowNode
            WindowSample.Navbar -> NavBarWindowNode
            WindowSample.Panel -> PanelWindowNode
            WindowSample.FullApp -> FullAppWindowNode
        }
        if (!activeComponents.contains(window)) {
            activeComponents.add(window)
        }
    }

    private fun closeDeepLinkWindow() {
        activeComponents.remove(DeepLinkDemoNode)
    }

    private fun closeDrawerWindowNode() {
        activeComponents.remove(DrawerWindowNode)
    }

    private fun closeNavBarWindowNode() {
        activeComponents.remove(NavBarWindowNode)
    }

    private fun closePanelWindowNode() {
        activeComponents.remove(PanelWindowNode)
    }

    private fun closeFullAppWindowNode() {
        activeComponents.remove(FullAppWindowNode)
    }

    private fun exit() {
        activeComponents.clear()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        for (component in activeComponents) {
            key(component) {
                component.Content(modifier)
            }
        }
    }

}