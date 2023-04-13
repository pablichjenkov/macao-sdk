package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.demo.CustomTopBarComponent
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.drawer.NavigationDrawerState
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.platform.DiContainer
import com.pablichj.incubator.uistate3.platform.DispatchersProxy

object DrawerTreeBuilder {
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
    private lateinit var drawerComponent: DrawerComponent

    fun build(): DrawerComponent {

        if (DrawerTreeBuilder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home") {},
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings") {},
            )
        )

        return DrawerComponent(
            config = DrawerComponent.DefaultConfig,
            diContainer = diContainer
        ).also {
            drawerComponent = it
            it.setNavItems(drawerNavItems, 0)
            /*it.setDrawerComponentView { modifier: Modifier, childComponent: Component, navigationDrawerState: NavigationDrawerState ->
                Box(Modifier.fillMaxSize()) {
                    childComponent.Content(Modifier)
                    Text(modifier = Modifier.align(Alignment.Center),
                        text = "You should provide a Composable that encloses the render of childComponent.Content(Modifier)",
                        color = Color.Black
                    )
                }
            }*/
        }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders/Active") {},

                ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders/Past") {},

                ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Orders/New Order") {},

                )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}