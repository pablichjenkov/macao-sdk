package com.pablichj.incubator.uistate3.example.helloWorld

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.pablichj.incubator.uistate3.DesktopBridge
import com.pablichj.incubator.uistate3.DesktopComponentRender
import com.pablichj.incubator.uistate3.platform.DefaultAppLifecycleDispatcher
import kotlin.system.exitProcess

fun main() =
    singleWindowApplication(
        title = "Chat",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        MaterialTheme {
            DesktopComponentRender(
                rootComponent = HelloWorldComponent(),
                desktopBridge = DesktopBridge(
                    appLifecycleDispatcher = DefaultAppLifecycleDispatcher(),
                    onBackPressEvent = { exitProcess(0) }
                ),
            )
        }
    }
