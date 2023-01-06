package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeNode
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode
import com.pablichj.incubator.uistate3.node.panel.PanelNode

class AdaptableSizeStateTreeHolder : ViewModel() {

    private lateinit var AdaptableSizeNode: AdaptableSizeNode
    private lateinit var subTreeNavItems: MutableList<NodeItem>

    fun getOrCreate(
        windowSizeInfoProvider: IWindowSizeInfoProvider,
    ): AdaptableSizeNode {

        if (this::AdaptableSizeNode.isInitialized) {
            return AdaptableSizeNode.apply {
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeNode(
            windowSizeInfoProvider
        ).also {
            it.setNavItems(
                getOrCreateDetachedNavItems(), 0
            )
            it.setCompactContainer(DrawerNode())
            it.setMediumContainer(NavBarNode())
            it.setExpandedContainer(PanelNode())
            AdaptableSizeNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NodeItem> {

        if (this::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val NavBarNode = NavBarNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode("Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode("Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        NavBarNode.setItems(navbarNavItems, 0)

        val navItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    "Home",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = NavBarNode,
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode(
                    "Settings",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}