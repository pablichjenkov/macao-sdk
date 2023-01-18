package com.pablichj.incubator.uistate3

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

/*

@Composable
fun ComposeApp(
    onExit: () -> Unit = {}
) {

    val timeText = remember {
        mutableStateOf("")
    }

    val DrawerNode = remember {
        DrawerTreeBuilder.build(onExit).also { it.start() }
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DrawerNode.Content(Modifier)
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(start = 56.dp, top = 56.dp, end = 16.dp),
                text = timeText.value
            )
        }
    }

    LaunchedEffect(Unit) {
        var timeMs = timestampMs()
        while (true) {
            timeText.value = "Time Elapsed: $timeMs milliSec"
            delay(1000)
            timeMs = timestampMs()
        }
    }

}*/
@Composable
internal fun ComposeApp(
    onExit: () -> Unit = {}
) {

    val ComposeAppState = remember { AppStateHolder.ComposeAppState }

    MaterialTheme {
        ComposeAppState.PresentContent(onExit)
    }

    LaunchedEffect(ComposeAppState) {
        ComposeAppState.start()
    }

}