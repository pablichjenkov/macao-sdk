package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.JvmBackPressDispatcher
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.example.statetrees.FullAppWithIntroTreeBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullAppWindowNode(
    parentContext: NodeContext,
    val onCloseClick: () -> Unit
) : Node(parentContext), WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))

    private var activeNode: Node = FullAppWithIntroTreeBuilder.build(
        JvmBackPressDispatcher(),
        backPressedCallback = object : BackPressedCallback() {
            override fun onBackPressed() {}
        }
    )

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            activeNode.Content(Modifier)
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(activeNode, it)
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