package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.split.SplitNavComponent
import example.nodes.AppCoordinatorComponent
import example.nodes.CustomTopBarComponent

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
                CustomTopBarComponent("Orders / Current", Icons.Filled.Edit) {}
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders / Current", Icons.Filled.Home) {},

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
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},

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
                component = CustomTopBarComponent("Orders / Current", Icons.Filled.Home) {},

            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders / Past", Icons.Filled.Edit) {},

            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Orders / Claim", Icons.Filled.Email) {},

            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},

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