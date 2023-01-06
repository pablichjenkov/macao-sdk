package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.AppCoordinatorNode
import example.nodes.OnboardingNode
import example.nodes.SplitNavNode

class FullAppIntroStateTreeHolder : ViewModel() {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AppCoordinatorNode: Node

    fun getOrCreate(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): Node {

        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (this@FullAppIntroStateTreeHolder::AppCoordinatorNode.isInitialized) {
            return AppCoordinatorNode
        }

        return AppCoordinatorNode(rootParentNodeContext).also {
            it.HomeNode = buildDrawerActivityStateTree(it.context)
            AppCoordinatorNode = it
        }
    }

    private fun buildDrawerActivityStateTree(parentContext: NodeContext): Node {

        val DrawerNode = DrawerNode(parentContext)

        val NavBarNode = NavBarNode(DrawerNode.context)
        val PagerNode = PagerNode(DrawerNode.context)

        val SplitNavNode = SplitNavNode(NavBarNode.context).apply {
            TopNode = buildNestedDrawer(context)
            BottomNode =
                OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Edit) {}
        }

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            ),
            NodeItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                node = SplitNavNode,
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NodeItem(
                label = "Account",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / Account",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / Profile",
                    Icons.Filled.Edit
                ) {},
                selected = false
            ),
            NodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / About Us",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(DrawerNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setItems(navbarNavItems, 0) },
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = PagerNode.also { it.setItems(pagerNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.apply {
            setItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(parentContext: NodeContext): DrawerNode {

        val DrawerNode = DrawerNode(parentContext)
        val NavBarNode = NavBarNode(DrawerNode.context)

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                node = OnboardingNode(DrawerNode.context, "Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setItems(navbarNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.also { it.setItems(drawerNavItems, 0) }
    }

}