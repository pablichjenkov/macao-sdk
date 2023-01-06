package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode

object NavBarTreeBuilder {

    private lateinit var NavBarNode: NavBarNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): NavBarNode {

        if (NavBarTreeBuilder::NavBarNode.isInitialized) {
            return NavBarNode
        }

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