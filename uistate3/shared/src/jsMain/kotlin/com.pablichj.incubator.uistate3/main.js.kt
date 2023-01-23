package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

@Composable
fun BrowserNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit
) {
    val webBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides webBackPressDispatcher,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

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

            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { webBackPressDispatcher.dispatchBackPressed() }
            )
        }
    }

}