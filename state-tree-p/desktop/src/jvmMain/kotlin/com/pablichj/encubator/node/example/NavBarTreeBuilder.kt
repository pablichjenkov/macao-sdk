package com.pablichj.encubator.node.example

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.encubator.node.NavigatorNodeItem
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode

object NavBarTreeBuilder {

    fun build(parentContext: NodeContext): NavBarNode {

        val NavBarNode = NavBarNode(parentContext)

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                node = OnboardingNode(NavBarNode.context, "Orders", Icons.Filled.Settings) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                node = OnboardingNode(NavBarNode.context, "Settings", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}