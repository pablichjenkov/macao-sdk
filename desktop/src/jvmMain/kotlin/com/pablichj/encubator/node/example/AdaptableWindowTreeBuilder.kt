package com.pablichj.encubator.node.example

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.adaptable.AdaptableWindowNode
import com.pablichj.encubator.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode

object AdaptableWindowTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AdaptableWindowNode: AdaptableWindowNode
    private lateinit var subTreeNavItems: MutableList<NavigatorNodeItem>

    fun build(
        windowSizeInfoProvider: IWindowSizeInfoProvider,
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): AdaptableWindowNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (AdaptableWindowTreeBuilder::AdaptableWindowNode.isInitialized) {
            return AdaptableWindowNode.apply {
                this.context.parentContext = rootParentNodeContext
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableWindowNode(
            rootParentNodeContext,
            windowSizeInfoProvider
        ).also {
            it.context.subPath = SubPath("AdaptableWindow")
            AdaptableWindowNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavigatorNodeItem> {

        if (AdaptableWindowTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        // Temporary empty context until the navItems get attached to a NavigatorNode.
        // At that point the navItems parent context will point to the NavigatorNode's context
        val TemporalEmptyContext = NodeContext(null)

        val NavBarNode = NavBarNode(TemporalEmptyContext)
            .apply { context.subPath = SubPath("Orders") }

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    NavBarNode.context, "Orders / Current", Icons.Filled.Home, {}
                ).apply { context.subPath = SubPath("Current") },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(
                    NavBarNode.context, "Orders / Past", Icons.Filled.Edit, {}
                ).apply { context.subPath = SubPath("Past") },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email, {})
                    .apply { context.subPath = SubPath("Claim") },
                selected = false
            )
        )

        NavBarNode.setNavItems(navbarNavItems, 0)

        val SettingNode =
            OnboardingNode(TemporalEmptyContext, "Settings", Icons.Filled.Email, {})
                .apply { context.subPath = SubPath("Settings") }

        val navItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    TemporalEmptyContext, "Home", Icons.Filled.Home, {}
                ).apply { context.subPath = SubPath("Home") },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = NavBarNode,
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = SettingNode,
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}