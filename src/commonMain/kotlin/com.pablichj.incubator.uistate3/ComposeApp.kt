package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import treebuilder.DrawerTreeBuilder

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
