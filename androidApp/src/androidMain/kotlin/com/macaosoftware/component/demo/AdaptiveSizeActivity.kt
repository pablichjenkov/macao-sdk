package com.macaosoftware.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerComponentDelegate
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponentDelegate
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelComponentDelegate
import com.macaosoftware.platform.AndroidBridge
import com.macaosoftware.component.demo.componentDelegates.NavBarComponentDelegate1
import com.macaosoftware.component.demo.treebuilders.AdaptableSizeTreeBuilder

class AdaptiveSizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        val rootComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(navItems, 0)
            it.setCompactContainer(
                DrawerComponent<DrawerStatePresenterDefault>(
                    drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
                    componentDelegate = DrawerComponentDelegate(),
                    content = DrawerComponentDefaults.DrawerComponentView
                )
            )
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                    componentDelegate = NavBarComponentDelegate(),
                    content = NavBarComponentDefaults.NavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
                    componentDelegate = PanelComponentDelegate(),
                    content = PanelComponentDefaults.PanelComponentView
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

    val navbarItems = listOf(
        NavItem(
            "Tab 1",
            Icons.Default.Email,
            SimpleComponent(
                "Tab 1",
                Color.Magenta,
            ) {}
        ),
        NavItem(
            "Tab 2",
            Icons.Default.AccountBox,
            SimpleComponent(
                "Tab 2",
                Color.Blue,
            ) {}
        )
    )

    val navBarComponent = NavBarComponent(
        navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
        componentDelegate = NavBarComponentDelegate1(),
        content = NavBarComponentDefaults.NavBarComponentView
    ).also {
        it.setNavItems(navItems = navbarItems, selectedIndex = 0)
    }

    MaterialTheme {
        AndroidComponentRender(
            rootComponent = navBarComponent,
            AndroidBridge(),
            onBackPress = { }
        )
    }

}
