package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.PanelTreeBuilder
import com.pablichj.incubator.uistate3.node.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PanelWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState()

    private var panelComponent: Component = PanelTreeBuilder.build()

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopNodeRender(
                rootComponent = panelComponent,
                onBackPressEvent = { onCloseClick }
            )
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(panelComponent, it)
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