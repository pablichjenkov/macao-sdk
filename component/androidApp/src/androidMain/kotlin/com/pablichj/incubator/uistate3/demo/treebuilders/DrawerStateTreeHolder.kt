package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.PagerComponent
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.demo.CustomTopBarComponent

class DrawerStateTreeHolder : ViewModel() {

    private lateinit var drawerComponent: DrawerComponent

    fun getOrCreate(): DrawerComponent {

        if (this@DrawerStateTreeHolder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val TopBarNode = CustomTopBarComponent(
            "Home",
            Icons.Filled.Home
        ) {}

        val NavBarNode = NavBarComponent()
        val PagerComponent = PagerComponent()

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

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Settings / Account",
                    Icons.Filled.Home,
                    {}
                ),//.apply{ deepLinkMatcher = { it == "Settings" } }, todo: explore why it crashes
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
                    Icons.Filled.Email,
                    {}
                ),
            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarNode,
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = PagerComponent.apply {
                    deepLinkMatcher = { route -> route == "Settings" }
                    setNavItems(pagerNavItems, 0)
                },
            )
        )

        return DrawerComponent().apply {
            drawerComponent = this
            setNavItems(
                drawerNavItems, 0
            )
        }

    }

}