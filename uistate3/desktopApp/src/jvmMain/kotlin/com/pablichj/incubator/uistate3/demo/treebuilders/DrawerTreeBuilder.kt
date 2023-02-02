package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setItems
import example.nodes.TopBarComponent

object DrawerTreeBuilder {

    private lateinit var DrawerNode: DrawerComponent

    fun build(): DrawerComponent {

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        val DrawerNode = DrawerComponent()

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return DrawerNode.also { it.setItems(drawerNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders/Active", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent("Orders/Past", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent("Orders/New Order", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}