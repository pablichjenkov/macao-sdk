package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import example.nodes.DefaultBackPressDispatcher
import kotlinx.coroutines.delay
import treebuilder.DrawerTreeBuilder
import androidx.compose.runtime.mutableStateOf

@Composable
fun ComposeApp() {
    val coroutineScope = rememberCoroutineScope()
    var timeText = remember {
        mutableStateOf("")
    }

    val DrawerNode = remember {
        DrawerTreeBuilder.build(
            backPressDispatcher = DefaultBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        ).also { it.start() }
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            /*Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Chat sample") }
                    )
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(Modifier.weight(1f)) {
                        Messages(state.messages)
                    }
                    SendMessage { text ->
                        store.send(
                            Action.SendMessage(
                                Message(myUser, timeMs = com.pablichj.incubator.uistate3.timestampMs(), text)
                            )
                        )
                    }
                }
            }*/
            DrawerNode.Content(Modifier)
            Text(timeText.value)
        }
    }
    LaunchedEffect(Unit) {
        var timeMs = timestampMs()
        var counter = 0
        while (true) {
            timeText.value = "Time Elapsed: $timeMs seconds"
            delay(1000)
            counter++
            timeMs = timestampMs()
        }
    }
}
