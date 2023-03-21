package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.demo.AppCoordinatorComponent
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.PagerComponent
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.node.split.SplitNavComponent
import com.pablichj.incubator.uistate3.demo.CustomTopBarComponent

class FullAppIntroStateTreeHolder : ViewModel() {

    private lateinit var appCoordinatorComponent: Component

    fun getOrCreate(): Component {

        if (this@FullAppIntroStateTreeHolder::appCoordinatorComponent.isInitialized) {
            return appCoordinatorComponent
        }

        return AppCoordinatorComponent().also {
            buildDrawerActivityStateTree(it).also { homeNode ->
                it.homeComponent = homeNode
            }
            appCoordinatorComponent = it
        }
    }

    private fun buildDrawerActivityStateTree(parentComponent: Component): Component {

        val DrawerNode = DrawerComponent()

        val NavBarNode = NavBarComponent()
        val PagerNode = PagerComponent()

        val SplitNavNode = SplitNavComponent().apply {
            setTopNode(buildNestedDrawer())
            setBottomNode(CustomTopBarComponent("Orders / Current", Icons.Filled.Edit) {})
        }

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
            ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = SplitNavNode,
            )
        )

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Settings / Account",
                    Icons.Filled.Home
                ) {},
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent(
                    "Settings / Profile",
                    Icons.Filled.Edit
                ) {},
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent(
                    "Settings / About Us",
                    Icons.Filled.Email
                ) {},
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
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = PagerNode.also { it.setNavItems(pagerNavItems, 0) },
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