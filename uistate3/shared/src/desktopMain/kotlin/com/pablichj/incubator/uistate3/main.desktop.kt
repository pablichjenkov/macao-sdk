package com.pablichj.incubator.uistate3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

@Composable
fun DesktopNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit
) {
    val jvmBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides jvmBackPressDispatcher,
    ) {

        val composeAppState = remember {
            AppStateHolder.ComposeAppState.also {
                it.setRootNode(rootNode)
                it.setBackPressHandler(onBackPressEvent)
            }
        }

        composeAppState.PresentContent()

        LaunchedEffect(composeAppState) {
            composeAppState.start()
        }

    }

}