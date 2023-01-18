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

@Composable
fun HelloWorldApp() {
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


