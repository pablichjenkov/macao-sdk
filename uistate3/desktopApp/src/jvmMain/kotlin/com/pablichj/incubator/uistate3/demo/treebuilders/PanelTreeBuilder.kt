package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import com.pablichj.incubator.uistate3.node.setItems
import example.nodes.TopBarComponent

object PanelTreeBuilder {

    private lateinit var PanelNode: PanelComponent

    fun build(): PanelComponent {

        if (PanelTreeBuilder::PanelNode.isInitialized) {
            return PanelNode
        }

        val PanelNode = PanelComponent()

        val panelNavItems = mutableListOf(
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

        return PanelNode.also { it.setItems(panelNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = TopBarComponent("Orders", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = TopBarComponent("Settings", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}