package com.pablichj.incubator.uistate3.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pablichj.incubator.uistate3.AndroidComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.incubator.uistate3.demo.treebuilders.DrawerTreeBuilder
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import com.pablichj.incubator.uistate3.platform.DiContainer
import com.pablichj.incubator.uistate3.platform.DispatchersProxy

class AdaptableSizeNodeActivity : ComponentActivity() {

    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        val rootComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent(
                    config = DrawerComponent.DefaultConfig,
                    diContainer = diContainer
                )
            )
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent())
        }
        setContent {
            AndroidComponentRender(
                rootComponent = rootComponent,
                onBackPressEvent = { finish() }
            )
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