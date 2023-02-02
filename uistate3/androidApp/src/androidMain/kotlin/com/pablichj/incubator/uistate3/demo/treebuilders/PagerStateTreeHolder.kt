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
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders/ Current") {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Orders / Past") {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Orders / Claim") {},
                selected = false
            )
        )

        val navbarNavItems2 = mutableListOf(
            NodeItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Settings / Account") {},
                selected = false
            ),
            NodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Settings / Profile") {},
                selected = false
            ),
            NodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Settings / About Us") {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Home") {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode1.also { it.setItems(navbarNavItems1, 0) },
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = NavBarNode2.also { it.setItems(navbarNavItems2, 0) },
                selected = false
            )
        )

        return PagerNode.also { it.setItems(pagerNavItems, 0) }
    }

}