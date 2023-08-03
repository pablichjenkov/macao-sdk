package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider

@Composable
fun BrowserComponentRender(
    rootComponent: Component,
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
        rootComponent.dispatchStart()
    }

}
