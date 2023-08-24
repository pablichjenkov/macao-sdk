package com.pablichj.templato.component.core

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.pablichj.templato.component.core.backpress.AndroidBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.platform.AndroidBridge

@Composable
fun AndroidComponentRender(
    rootComponent: Component,
    androidBridge: AndroidBridge,
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

@Preview
@Composable
fun AndroidComponentRenderPreview() {

    val simpleComponent = object : Component() {
        @Composable
        override fun Content(modifier: Modifier) {
            Column {
                Text(text = "Previewing a Component!")
                Text(text = "Previewing a Component!")
            }
        }
    }

    val simpleComponent2 = object : Component() {
        @Composable
        override fun Content(modifier: Modifier) {
            Column {
                Text(text = "Previewing a Component2!")
                Text(text = "Previewing a Component2!")
            }
        }
    }

    val drawerItems = listOf(
        NavItem(
            "simpleComponent",
            Icons.Default.Close,
            simpleComponent
        ),
        NavItem(
            "simpleComponent2",
            Icons.Default.Close,
            simpleComponent2
        )
    )

    val drawerComponent = DrawerComponent(
        drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
        content = DrawerComponent.DefaultDrawerComponentView
    ).also {
        it.setNavItems(navItems = drawerItems, selectedIndex = 1)
    }

    AndroidComponentRender(
        rootComponent = drawerComponent,
        androidBridge = AndroidBridge(),
        onBackPress = {}
    )

}
