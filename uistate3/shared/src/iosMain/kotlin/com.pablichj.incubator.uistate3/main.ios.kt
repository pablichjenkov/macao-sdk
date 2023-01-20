package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import platform.UIKit.UIViewController
import platform.posix.close
import kotlin.system.exitProcess

fun MainViewController(): UIViewController =
    Application("State3 Demo") {

        val backPressDispatcher = remember {
            DefaultBackPressDispatcher()
        }

        CompositionLocalProvider(
            LocalBackPressedDispatcher provides backPressDispatcher,
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
                    onClick = { backPressDispatcher.dispatchBackPressed() }
                )
            }
        }
        
    }