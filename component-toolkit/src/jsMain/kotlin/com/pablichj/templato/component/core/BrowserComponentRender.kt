package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.macaosoftware.component.backpress.DefaultBackPressDispatcher
import com.macaosoftware.component.backpress.LocalBackPressedDispatcher
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.pablichj.templato.component.platform.JsBridge

@Composable
fun BrowserComponentRender(
    rootComponent: Component,
    jsBridge: JsBridge,
    onBackPress: () -> Unit = {}
) {
    val webBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }
    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides webBackPressDispatcher,
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
        /*Box(modifier = Modifier.fillMaxSize()) {
            // Should listen for keyboard back instead
            FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { webBackPressDispatcher.dispatchBackPressed() }
            )
        }*/
    }

    LaunchedEffect(key1 = rootComponent) {
        rootComponent.isRoot = true
        rootComponent.rootBackPressDelegate = updatedOnBackPressed
        rootComponent.dispatchStart()
    }

}
