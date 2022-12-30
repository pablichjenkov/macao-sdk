package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import example.treebuilder.DrawerTreeBuilder

@Composable
fun ComposeApp(
    onExit: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    val timeText = remember {
        mutableStateOf("")
    }

    val backPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    val DrawerNode = remember {
        DrawerTreeBuilder.build(
            backPressDispatcher = backPressDispatcher,
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {
                    onExit()
                }
            }
        ).also { it.start() }
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
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { backPressDispatcher.dispatchBackPressed() }
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

}
