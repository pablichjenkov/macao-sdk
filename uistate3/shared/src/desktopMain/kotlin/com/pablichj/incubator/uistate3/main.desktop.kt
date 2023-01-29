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
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

@Composable
fun DesktopNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit
) {
    val desktopBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher
    ) {
        rootNode.Content(Modifier.fillMaxSize())
    }

    LaunchedEffect(key1 = rootNode, key2 = onBackPressEvent) {
        rootNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            onBackPressEvent()
        }
        rootNode.start()
    }

    /*CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher,
    ) {

        val rootState = remember {
            RootState().also {
                it.setRootNode(rootNode)
                it.setBackPressHandler(onBackPressEvent)
            }
        }

        Box {
            rootState.PresentContent()
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { desktopBackPressDispatcher.dispatchBackPressed() }
            )

        }

        LaunchedEffect(rootState) {
            rootState.start()
        }

    }*/

}

/*
@Composable
fun AndroidNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit = {}
) {


    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Pablo Receiving Activity.onStart() event")
            if (rootNode.context.lifecycleState != Node.LifecycleState.Started) {
                rootNode.start()
            }
        },
        onStop = {
            println("Pablo Receiving Activity.onStop() event")
            if (rootNode.context.lifecycleState != Node.LifecycleState.Stopped) {
                rootNode.stop()
            }
        }
    )

    val activity = LocalContext.current as ComponentActivity



}*/
