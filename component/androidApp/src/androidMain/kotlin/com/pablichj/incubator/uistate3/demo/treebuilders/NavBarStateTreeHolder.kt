package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import example.nodes.CustomTopBarComponent

class NavBarStateTreeHolder : ViewModel() {

    private lateinit var NavBarNode: NavBarComponent

    fun getOrCreate(): Component {

        if (this::NavBarNode.isInitialized) {
            return NavBarNode
        }

        NavBarNode = NavBarComponent()
        val PagerNode = PagerComponent()

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

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders", Icons.Filled.Edit) {},
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = PagerNode.also { it.setNavItems(pagerNavItems, 0) },
            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}