package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode
import com.pablichj.incubator.uistate3.node.panel.PanelNode

object PanelTreeBuilder {

    private lateinit var PanelNode: PanelNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): PanelNode {

        if (PanelTreeBuilder::PanelNode.isInitialized) {
            return PanelNode
        }

        val PanelNode = PanelNode()

        val panelNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = buildNavBarNode(PanelNode.context),
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return PanelNode.also { it.setItems(panelNavItems, 0) }
    }

    private fun buildNavBarNode(parentContext: NodeContext): NavBarNode {

        val NavBarNode = NavBarNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                node = OnboardingNode("Orders", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                node = OnboardingNode("Settings", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}