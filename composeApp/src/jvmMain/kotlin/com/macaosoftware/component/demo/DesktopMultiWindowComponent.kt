package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component

class DesktopMultiWindowComponent : Component() {
    private val activeComponents = mutableStateListOf<Component>()

    private val mainWindowComponent = MainWindowComponent(
        onOpenDeepLinkClick = {
            openDeepLinkWindow()
        },
        onMenuItemClick = {
            // openWindow(it)
        },
        onExitClick = { exit() }
    )

    private val deepLinkDemoComponent = DeepLinkDemoWindowComponent(
        onDeepLinkClick = { path ->
            mainWindowComponent.handleDeepLink(path)
        },
        onCloseClick = {
            closeDeepLinkWindow()
        }
    )

    init {
        activeComponents.add(mainWindowComponent)
    }

    private fun openDeepLinkWindow() {
        if (!activeComponents.contains(deepLinkDemoComponent)) {
            activeComponents.add(deepLinkDemoComponent)
        }
    }

    private fun closeDeepLinkWindow() {
        activeComponents.remove(deepLinkDemoComponent)
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
