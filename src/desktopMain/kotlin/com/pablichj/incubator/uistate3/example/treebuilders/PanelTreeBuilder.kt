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

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var PanelNode: PanelNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): PanelNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (PanelTreeBuilder::PanelNode.isInitialized) {
            return PanelNode
        }

        val PanelNode = PanelNode(rootParentNodeContext)

        val panelNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(PanelNode.context, "Home", Icons.Filled.Home) {},
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
                node = OnboardingNode(PanelNode.context, "Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return PanelNode.also { it.setItems(panelNavItems, 0) }
    }

    private fun buildNavBarNode(parentContext: NodeContext): NavBarNode {

        val NavBarNode = NavBarNode(parentContext)

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                node = OnboardingNode(NavBarNode.context, "Orders", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                node = OnboardingNode(NavBarNode.context, "Settings", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}