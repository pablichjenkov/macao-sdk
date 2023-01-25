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
import kotlin.system.exitProcess

class NavBarWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState()

    private var NavBarNode: Node = NavBarTreeBuilder.build()

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopNodeRender(
                rootNode = NavBarNode,
                onBackPressEvent = { onCloseClick }
            )
            //NavBarNode.Content(Modifier)
            //context.rootNodeBackPressedDelegate = ForwardBackPressCallback { exitProcess(0) }
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(NavBarNode, it)
                    }
                    .launchIn(this)
            }
        }
    }

    private fun onWindowMinimized(RootNode: Node, minimized: Boolean) {
        if (minimized) {
            RootNode.stop()
        } else {
            RootNode.start()
        }
    }

}