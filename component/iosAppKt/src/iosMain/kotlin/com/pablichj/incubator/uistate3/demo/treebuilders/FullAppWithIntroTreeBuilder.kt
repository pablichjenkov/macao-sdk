package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.incubator.uistate3.demo.AppCoordinatorComponent
import com.pablichj.incubator.uistate3.demo.CustomTopBarComponent
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.node.split.SplitNavComponent

object FullAppWithIntroTreeBuilder {

    private lateinit var AppCoordinatorComponent: Component

    fun build(): Component {

        if (FullAppWithIntroTreeBuilder::AppCoordinatorComponent.isInitialized) {
            return AppCoordinatorComponent
        }

        return AppCoordinatorComponent().also {
            it.homeComponent = buildDrawerStateTree(it)
            AppCoordinatorComponent = it
        }
    }

    private fun buildDrawerStateTree(parentComponent: Component): Component {
        val DrawerNode = DrawerComponent()
        val NavBarNode = NavBarComponent()

        val SplitNavNode = SplitNavComponent().apply {
            setTopNode(buildNestedDrawer())
            setBottomNode(
                CustomTopBarComponent("Orders / Current") {}
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders / Current") {},

                ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = SplitNavNode,

                )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home") {},

                ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },

                )
        )

        return DrawerNode.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent {
        val DrawerNode = DrawerComponent()
        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders / Current") {},

                ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders / Past") {},

                ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Orders / Claim") {},

                )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home") {},

                ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },

                )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }

}