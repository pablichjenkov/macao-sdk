package com.pablichj.encubator.node.example.builders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode

object PagerActivityTreeBuilder {

    private val rootParentNodeContext = NodeContext(null)
    private lateinit var PagerNode: PagerNode

    fun build(
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): Node {

        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate =
            backPressedCallback

        if (PagerActivityTreeBuilder::PagerNode.isInitialized) {
            return PagerNode
        }

        PagerNode = PagerNode(rootParentNodeContext)

        val NavBarNode1 = NavBarNode(PagerNode.context)
        val NavBarNode2 = NavBarNode(PagerNode.context)

        val navbarNavItems1 = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode1.context, "Orders/ Current") {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode1.context, "Orders / Past") {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode1.context, "Orders / Claim") {},
                selected = false
            )
        )

        val navbarNavItems2 = mutableListOf(
            NavigatorNodeItem(
                label = "Account",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode2.context, "Settings / Account") {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode2.context, "Settings / Profile") {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode2.context, "Settings / About Us") {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(PagerNode.context, "Home") {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                node = NavBarNode1.also { it.setNavItems(navbarNavItems1, 0) },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = NavBarNode2.also { it.setNavItems(navbarNavItems2, 0) },
                selected = false
            )
        )

        return PagerNode.also { it.setNavItems(pagerNavItems, 0) }
    }

}