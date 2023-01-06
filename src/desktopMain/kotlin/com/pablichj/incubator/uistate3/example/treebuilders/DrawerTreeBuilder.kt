package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode

object DrawerTreeBuilder {

    private lateinit var DrawerNode: DrawerNode

    fun build(): DrawerNode {

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        val DrawerNode = DrawerNode()

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = buildNavBarNode(),
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return DrawerNode.also { it.setItems(drawerNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarNode {

        val NavBarNode = NavBarNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Active",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Orders/Active", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                node = OnboardingNode("Orders/Past", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                node = OnboardingNode("Orders/New Order", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}