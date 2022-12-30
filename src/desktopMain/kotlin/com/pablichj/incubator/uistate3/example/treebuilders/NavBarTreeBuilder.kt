package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NavigatorNodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import com.pablichj.incubator.uistate3.node.nodes.OnboardingNode

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