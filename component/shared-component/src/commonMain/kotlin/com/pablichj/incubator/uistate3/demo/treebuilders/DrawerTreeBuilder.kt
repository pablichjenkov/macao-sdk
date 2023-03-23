package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.demo.CustomTopBarComponent

object DrawerTreeBuilder {

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

        return DrawerComponent().also {
            it.setNavItems(drawerNavItems, 0)
            drawerComponent = it
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