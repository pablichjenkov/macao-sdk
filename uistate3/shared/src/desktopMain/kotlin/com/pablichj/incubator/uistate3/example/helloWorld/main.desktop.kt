package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.ComposeApp
import com.pablichj.incubator.uistate3.FloatingButton
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import kotlin.system.exitProcess

@Composable
fun HelloWorldApp() {
    val jvmBackPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides jvmBackPressDispatcher,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ComposeApp(
                onExit = {
                    exitProcess(0)
                }
            )
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { jvmBackPressDispatcher.dispatchBackPressed() }
            )
        }
    }
}