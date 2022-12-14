package com.pablichj.encubator.node.example

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.IBackPressDispatcher
import com.pablichj.encubator.node.NavigatorNodeItem
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.nodes.OnboardingNode

object DrawerTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var DrawerNode: DrawerNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): DrawerNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        val DrawerNode = DrawerNode(rootParentNodeContext)

        val drawerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(DrawerNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = NavBarTreeBuilder.build(DrawerNode.context),
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode(DrawerNode.context, "Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }

}