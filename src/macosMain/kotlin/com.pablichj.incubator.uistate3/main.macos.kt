package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import platform.posix.exit

fun main() {
    NSApplication.sharedApplication()

    Window("State3 App") {
        val macBackPressDispatcher = remember {
            DefaultBackPressDispatcher()
        }

        CompositionLocalProvider(
            LocalBackPressedDispatcher provides macBackPressDispatcher,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                ComposeApp(
                    onExit = { exit(0) }
                )
                FloatingButton(
                    modifier = Modifier.offset(y = 48.dp),
                    alignment = Alignment.TopStart,
                    onClick = { macBackPressDispatcher.dispatchBackPressed() }
                )
            }
        }
    }

    NSApp?.run()
}