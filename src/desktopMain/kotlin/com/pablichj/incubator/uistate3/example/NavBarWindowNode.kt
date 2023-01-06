package com.pablichj.incubator.uistate3.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.example.treebuilders.NavBarTreeBuilder
import com.pablichj.incubator.uistate3.node.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class NavBarWindowNode(
    val onCloseClick: () -> Unit
) : Node(), WindowNode {
    private val windowState = WindowState()

    private var NavBarNode: Node = NavBarTreeBuilder.build(
        backPressDispatcher = DefaultBackPressDispatcher(),
        backPressedCallback = ForwardBackPressCallback { exitProcess(0) }
    )

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            NavBarNode.Content(Modifier)
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