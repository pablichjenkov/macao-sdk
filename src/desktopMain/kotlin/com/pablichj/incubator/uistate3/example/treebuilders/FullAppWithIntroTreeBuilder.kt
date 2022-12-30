package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import com.pablichj.incubator.uistate3.node.nodes.AppCoordinatorNode
import com.pablichj.incubator.uistate3.node.nodes.OnboardingNode
import com.pablichj.incubator.uistate3.node.nodes.SplitNavNode

object FullAppWithIntroTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AppCoordinatorNode: Node

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): Node {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (FullAppWithIntroTreeBuilder::AppCoordinatorNode.isInitialized) {
            return AppCoordinatorNode
        }

        return AppCoordinatorNode(rootParentNodeContext).also {
            it.HomeNode = buildDrawerStateTree(it.context)
            AppCoordinatorNode = it
        }
    }

    private fun buildDrawerStateTree(parentContext: NodeContext): Node {

        val DrawerNode = DrawerNode(parentContext)

        val NavBarNode = NavBarNode(DrawerNode.context)

        val SplitNavNode = SplitNavNode(NavBarNode.context).apply {
            TopNode = buildNestedDrawer(context)
            BottomNode = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Edit) {}
        }

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                node = SplitNavNode,
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(DrawerNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.apply {
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(parentContext: NodeContext): DrawerNode {

        val DrawerNode = DrawerNode(parentContext)
        val NavBarNode = NavBarNode(DrawerNode.context)

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                node = OnboardingNode(DrawerNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }

}