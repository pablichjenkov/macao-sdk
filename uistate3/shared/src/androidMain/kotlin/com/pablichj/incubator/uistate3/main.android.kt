package com.pablichj.incubator.uistate3

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

@Composable
fun AndroidNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit = {}
) {
    val rootState = remember {
        RootState().also {
            it.setRootNode(rootNode)
            it.setBackPressHandler(onBackPressEvent)
        }
    }

    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
    ) {
        rootState.PresentContent()
    }

    LaunchedEffect(rootState) {
        rootState.start()
    }
}