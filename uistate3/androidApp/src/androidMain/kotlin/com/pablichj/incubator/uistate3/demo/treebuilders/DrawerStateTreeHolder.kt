package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.PagerComponent
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setItems
import example.nodes.TopBarComponent

class DrawerStateTreeHolder : ViewModel() {

    private lateinit var DrawerNode: DrawerComponent

    fun getOrCreate(): Component {

        if (this@DrawerStateTreeHolder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        DrawerNode = DrawerComponent()

        val TopBarNode = TopBarComponent(
            "Home",
            Icons.Filled.Home
        ) {}

        val NavBarNode = NavBarComponent()
        val PagerNode = PagerComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    "Settings / Account",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    "Settings / Profile",
                    Icons.Filled.Edit
                ) {},
                selected = false
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    "Settings / About Us",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarNode,
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also { it.setItems(navbarNavItems, 0) },
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = PagerNode.also { it.setItems(pagerNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.apply { setItems(drawerNavItems, 0) }
    }

}