package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import example.nodes.CustomTopBarComponent

object DrawerTreeBuilder {

    private lateinit var DrawerNode: DrawerComponent

    fun build(): DrawerComponent {

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        val DrawerNode = DrawerComponent()

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},

            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),

            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings", Icons.Filled.Email) {},

            )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders/Active", Icons.Filled.Home) {},

            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders/Past", Icons.Filled.Settings) {},

            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Orders/New Order", Icons.Filled.Add) {},

            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}