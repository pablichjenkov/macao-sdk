package com.pablichj.encubator.node.example.statetrees

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.IBackPressDispatcher
import com.pablichj.encubator.node.NavigatorNodeItem
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode

object NavBarTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var NavBarNode: NavBarNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): NavBarNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (NavBarTreeBuilder::NavBarNode.isInitialized) {
            return NavBarNode
        }

        val NavBarNode = NavBarNode(rootParentNodeContext)

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