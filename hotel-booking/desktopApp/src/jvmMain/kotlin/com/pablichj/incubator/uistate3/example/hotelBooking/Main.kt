package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.pablichj.templato.component.core.DesktopComponentRender
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.DesktopBridge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

fun main() {
    //todo: Use the adaptable node in this case
    val windowState = WindowState(size = DpSize(500.dp, 800.dp))
    val rootComponent = AppBuilder.buildGraph()
    val desktopBridge = DesktopBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher(),
        onBackPressEvent = { exitProcess(0) }
    )
    singleWindowApplication(
        title = "Hotel Booking",
        state = windowState
    ) {
        DesktopComponentRender(
            rootComponent = rootComponent,
            desktopBridge = desktopBridge
        )
        LaunchedEffect(window.state) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(desktopBridge.appLifecycleDispatcher, it)
                    }
                    .launchIn(this)
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
