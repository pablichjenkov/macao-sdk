package com.pablichj.incubator.uistate3

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node
import kotlin.system.exitProcess


/*
@Composable
fun DesktopComposeApp(RootNode: Node) {
    val jvmBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }
    MaterialTheme {
        CompositionLocalProvider(
            LocalBackPressedDispatcher provides jvmBackPressDispatcher,
            LocalLayoutDirection provides LayoutDirection.Ltr
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                */
/*ComposeApp(
                    onExit = {
                        exitProcess(0)
                    }
                )*//*

                //todo: don't use the RootNode here, pass it to ComposeAppState and use PresentContent
                // Pass the RootNode to ComposeApp and ComposeApp sets RootNode to ComposeState internally
                // or
                // Pass ComposeState to ComposeApp and sets RootNode to ComposeState externally
                val composeAppState = remember {
                    AppStateHolder.ComposeAppState.also { it.setRootNode(RootNode) }
                }


                RootNode.Content(Modifier)
                //ComposeAppState.PresentContent(onExit)

                LaunchedEffect(composeAppState) {
                    RootNode.start()
                    //ComposeAppState.start()
                }


                FloatingButton(
                    modifier = Modifier.offset(y = 48.dp),
                    alignment = Alignment.TopStart,
                    onClick = { jvmBackPressDispatcher.dispatchBackPressed() }
                )
            }
        }
    }

}*/

@Composable
fun DesktopComposeApp(
    RootNode: Node
) {
    val jvmBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }
    MaterialTheme {
        CompositionLocalProvider(
            LocalBackPressedDispatcher provides jvmBackPressDispatcher,
        ) {

            /*ComposeApp(
                onExit = {
                    exitProcess(0)
                }
            )*/

            //todo: don't use the RootNode here, pass it to ComposeAppState and use PresentContent
            // Pass the RootNode to ComposeApp and ComposeApp sets RootNode to ComposeState internally
            // or
            // Pass ComposeState to ComposeApp and sets RootNode to ComposeState externally
            val composeAppState = remember {
                AppStateHolder.ComposeAppState.also { it.setRootNode(RootNode) }
            }

            //RootNode.Content(Modifier)
            composeAppState.PresentContent(
                onBackPressEvent = {
                    exitProcess(0)
                }
            )

            LaunchedEffect(composeAppState) {
                //RootNode.start()
                composeAppState.start()
            }
        }
    }


}
