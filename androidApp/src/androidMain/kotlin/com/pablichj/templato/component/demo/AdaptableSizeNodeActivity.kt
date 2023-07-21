package com.pablichj.templato.component.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import com.pablichj.templato.component.core.AndroidComponentRender
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.NavigationDrawerStateDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

class AdaptableSizeNodeActivity : ComponentActivity() {

    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        val rootComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent<NavigationDrawerStateDefault>(
                    navigationDrawerState = DrawerComponent.createDefaultState(),
                    config = DrawerComponent.DefaultConfig,
                    diContainer = diContainer,
                    content = DrawerComponent.DefaultDrawerComponentView
                )
            )
            it.setMediumContainer(
                NavBarComponent(
                    navBarState = NavBarComponent.createDefaultState(),
                    config = NavBarComponent.DefaultConfig,
                    content = NavBarComponent.DefaultNavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelState = PanelComponent.createDefaultState(),
                    config = PanelComponent.DefaultConfig,
                    content = PanelComponent.DefaultPanelComponentView
                )
            )
        }
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = rootComponent,
                    onBackPressEvent = { finish() }
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