package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.DrawerTreeBuilder
import com.pablichj.incubator.uistate3.node.Component
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class DrawerWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState()

    private var drawerComponent: Component = DrawerTreeBuilder.build()/*.apply {
        context.rootNodeBackPressedDelegate = ForwardBackPressCallback { exitProcess(0) }
    }*/

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopNodeRender(
                rootComponent = drawerComponent,
                onBackPressEvent = { exitProcess(0) }
            )
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(drawerComponent, it)
                    }
                    .launchIn(this)
            }
        }
    }

    private fun onWindowMinimized(rootComponent: Component, minimized: Boolean) {
        if (minimized) {
            rootComponent.stop()
        } else {
            rootComponent.start()
        }
    }

}