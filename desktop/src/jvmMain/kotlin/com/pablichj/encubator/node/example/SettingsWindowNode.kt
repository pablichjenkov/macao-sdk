package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.JvmBackPressDispatcher
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsWindowNode(
    parentContext: NodeContext
) : WindowNode(parentContext) {

    var isActive = mutableStateOf(true)
    private var activeNode: Node

    init {
        activeNode = FullAppTreeBuilder.build(
            JvmBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        )
    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (!isActive.value) { return }

        Window(onCloseRequest = {
            isActive.value = false
        },
            windowState) {
            activeNode.Content(Modifier)
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
    }

    private fun onWindowMinimized(RootNode: Node, minimized: Boolean) {
        if (minimized) {
            RootNode.stop()
        } else {
            RootNode.start()
        }
    }

}