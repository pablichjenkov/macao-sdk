package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import example.nodes.TopBarComponent

class PagerStateTreeHolder : ViewModel() {

    private lateinit var PagerNode: PagerComponent

    fun getOrCreate(): Component {

        if (this@PagerStateTreeHolder::PagerNode.isInitialized) {
            return PagerNode
        }

        PagerNode = PagerComponent()

        val NavBarNode1 = NavBarComponent()
        val NavBarNode2 = NavBarComponent()

        val navbarNavItems1 = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders/ Current") {},
                selected = false
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Orders / Past") {},
                selected = false
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Orders / Claim") {},
                selected = false
            )
        )

        val navbarNavItems2 = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Settings / Account") {},
                selected = false
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Settings / Profile") {},
                selected = false
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Settings / About Us") {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Home") {},
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode1.also { it.setNavItems(navbarNavItems1, 0) },
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = NavBarNode2.also { it.setNavItems(navbarNavItems2, 0) },
                selected = false
            )
        )

        return PagerNode.also { it.setNavItems(pagerNavItems, 0) }
    }

}