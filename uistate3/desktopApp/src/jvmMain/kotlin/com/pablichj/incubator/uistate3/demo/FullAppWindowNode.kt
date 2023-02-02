package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.FullAppWithIntroTreeBuilder
import com.pablichj.incubator.uistate3.node.Component
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullAppWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))

    private var activeComponent: Component = FullAppWithIntroTreeBuilder.build()

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopNodeRender(
                rootComponent = activeComponent,
                onBackPressEvent = { onCloseClick }
            )
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(activeComponent, it)
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