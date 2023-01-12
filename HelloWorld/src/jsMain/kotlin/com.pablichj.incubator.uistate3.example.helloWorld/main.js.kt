package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import com.pablichj.incubator.uistate3.FloatingButton
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("Hello World") {
            val webBackPressDispatcher = remember {
                DefaultBackPressDispatcher()
            }

            CompositionLocalProvider(
                LocalBackPressedDispatcher provides webBackPressDispatcher,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ComposeApp(
                        onExit = {
                            // Should hide back button
                        }
                    )
                    FloatingButton(
                        modifier = Modifier.offset(y = 48.dp),
                        alignment = Alignment.TopStart,
                        onClick = { webBackPressDispatcher.dispatchBackPressed() }
                    )
                }
            }
        }
    }
}