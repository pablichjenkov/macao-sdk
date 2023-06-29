package com.pablichj.templato.component.core

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.templato.component.core.backpress.AndroidBackPressDispatcher
import com.pablichj.templato.component.core.backpress.BackPressHandler
import com.pablichj.templato.component.core.backpress.ForwardBackPressCallback
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher

@Composable
fun AndroidComponentRender(
    rootComponent: Component,
    onBackPressEvent: () -> Unit = {}
) {
    val updatedOnBackPressed by rememberUpdatedState(onBackPressEvent)

    LaunchedEffect(key1 = rootComponent) {
        rootComponent.onBackPressDelegationReachRoot = updatedOnBackPressed
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Receiving Activity.onStart() event")
            rootComponent.dispatchStart()
        },
        onStop = {
            println("Receiving Activity.onStop() event")
            rootComponent.dispatchStop()
        }
    )

    val activity = LocalContext.current as ComponentActivity

    MaterialTheme {
        CompositionLocalProvider(
            LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
        ) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }
}
