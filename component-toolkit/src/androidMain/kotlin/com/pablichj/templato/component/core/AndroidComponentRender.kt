package com.pablichj.templato.component.core

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.templato.component.core.backpress.AndroidBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider

@Composable
fun AndroidComponentRender(
    rootComponent: Component,
    onBackPress: () -> Unit = {}
) {

    val updatedOnBackPressed by rememberUpdatedState(onBackPress)
    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity),
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
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

    LaunchedEffect(rootComponent) {
        rootComponent.rootBackPressDelegate = updatedOnBackPressed
    }
}
