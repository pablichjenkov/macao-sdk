package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.NavBarTreeBuilder
import com.pablichj.incubator.uistate3.node.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NavBarWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState()

    private var navBarComponent: Component = NavBarTreeBuilder.build()

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopNodeRender(
                rootComponent = navBarComponent,
                onBackPressEvent = { onCloseClick }
            )
            //NavBarNode.Content(Modifier)
            //context.rootNodeBackPressedDelegate = ForwardBackPressCallback { exitProcess(0) }
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(navBarComponent, it)
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