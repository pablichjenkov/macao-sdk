package com.macaosoftware.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.preview.DrawerComponentViewModelPreviewAndroidRenderFactory
import com.macaosoftware.component.util.LocalBackPressedDispatcher
import com.macaosoftware.plugin.backpress.AndroidBackPressDispatcherPlugin
import com.macaosoftware.plugin.lifecycle.LifecycleEventObserver

@Composable
fun AndroidComponentRender(
    rootComponent: Component
) {

    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcherPlugin(activity),
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Receiving Activity.onStart() event")
            rootComponent.dispatchActive()
        },
        onStop = {
            println("Receiving Activity.onStop() event")
            rootComponent.dispatchInactive()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
        }
    )

}

@Preview
@Composable
private fun AndroidComponentRenderPreview() {

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

            Column(
                modifier = modifier.fillMaxSize().background(Color.LightGray)
            ) {
                Text(text = "Previewing a Component2!")
                Text(text = "Previewing a Component2! should change")
            }
        }
    }

    val drawerItems = listOf(
        NavItem(
            component = simpleComponent,
            label = "simpleComponent",
            icon = Icons.Default.Email
        ),
        NavItem(
            component = simpleComponent2,
            label = "simpleComponent2",
            icon = Icons.Default.Lock
        )
    )

    val drawerComponent = DrawerComponent(
        viewModelFactory = DrawerComponentViewModelPreviewAndroidRenderFactory(),
        content = DrawerComponentDefaults.DrawerComponentView
    ).also {
        it.setNavItems(navItems = drawerItems, selectedIndex = 0)
    }

    AndroidComponentRender(rootComponent = drawerComponent)
}
