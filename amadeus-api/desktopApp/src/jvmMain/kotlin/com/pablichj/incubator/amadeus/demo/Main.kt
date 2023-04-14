package com.pablichj.incubator.amadeus.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.pablichj.incubator.amadeus.storage.DriverFactory
import com.pablichj.incubator.amadeus.storage.createDatabase
import com.pablichj.templato.component.core.DesktopComponentRender
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.DesktopBridge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.system.exitProcess

fun main() {
    val windowState = WindowState(size = DpSize(500.dp, 800.dp))

    val database = createDatabase(DriverFactory())
    val amadeusDemoComponent = AmadeusDemoComponent(database)
    val desktopBridge = DesktopBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher(),
        onBackPressEvent = { }
    )

    singleWindowApplication(
        title = "Amadeus Desktop Demo",
        state = windowState,
        undecorated = true
    ) {
        LaunchedEffect(windowState) {
            snapshotFlow { windowState.isMinimized }
                .onEach {
                    onWindowMinimized(desktopBridge.appLifecycleDispatcher, it)
                }.launchIn(this)
        }
        MaterialTheme {
            Column {
                WindowDraggableArea {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.LightGray)
                    ) {
                        Row {
                            Spacer(modifier = Modifier.size(20.dp))
                            Box(
                                modifier = Modifier
                                    .padding(top = 14.dp, end = 8.dp)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .clickable {
                                        exitProcess(0)
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 14.dp, end = 8.dp)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color.Yellow)
                                    .clickable {
                                        windowState.isMinimized = true
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                                    .clickable {
                                        // Add code to maximize the window
                                    }
                            )
                        }
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                            Spacer(modifier = Modifier.size(20.dp))
                        }

                    }
                }
                DesktopComponentRender(
                    rootComponent = amadeusDemoComponent,
                    desktopBridge = desktopBridge
                )
            }
        }
    }
}

private fun onWindowMinimized(
    appLifecycleDispatcher: DefaultAppLifecycleDispatcher,
    minimized: Boolean
) {
    if (minimized) {
        appLifecycleDispatcher.dispatchAppLifecycleEvent(AppLifecycleEvent.Stop)
    } else {
        appLifecycleDispatcher.dispatchAppLifecycleEvent(AppLifecycleEvent.Start)
    }
}
