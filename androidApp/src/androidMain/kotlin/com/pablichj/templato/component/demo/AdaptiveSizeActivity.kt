package com.pablichj.templato.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pablichj.templato.component.core.AndroidComponentRender
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.platform.AndroidBridge
import com.pablichj.templato.component.platform.CoroutineDispatchers

class AdaptiveSizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        val rootComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent<DrawerStatePresenterDefault>(
                    drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
                    content = DrawerComponent.DefaultDrawerComponentView
                )
            )
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
                    content = NavBarComponent.DefaultNavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponent.createDefaultPanelStatePresenter(),
                    content = PanelComponent.DefaultPanelComponentView
                )
            )
        }
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = rootComponent,
                    AndroidBridge(),
                    onBackPress = { finish() }
                )
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

}

@Preview
@Composable
fun DrawerPreview() {

    val drawerItems = listOf(
        NavItem(
            "Tab 1",
            Icons.Default.Email,
            SimpleComponent(
                "Tab 1",
                Color.Magenta,
            ){}
        ),
        NavItem(
            "Tab 2",
            Icons.Default.AccountBox,
            SimpleComponent(
                "Tab 2",
                Color.Blue,
            ){}
        )
    )

    val navBarComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
        content = NavBarComponent.DefaultNavBarComponentView
    ).also {
        it.setNavItems(navItems = drawerItems, selectedIndex = 0)
    }

    MaterialTheme {
        AndroidComponentRender(
            rootComponent = navBarComponent,
            AndroidBridge(),
            onBackPress = { }
        )
    }

}
