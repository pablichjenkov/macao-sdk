package com.pablichj.incubator.uistate3.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.example.treebuilders.FullAppWithIntroTreeBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullAppWindowNode(
    parentContext: NodeContext,
    val onCloseClick: () -> Unit
) : Node(parentContext), WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))

    private var activeNode: Node = FullAppWithIntroTreeBuilder.build(
        DefaultBackPressDispatcher(),
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