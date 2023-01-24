package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.PagerNode
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.TopBarNode

class DrawerStateTreeHolder : ViewModel() {

    private lateinit var DrawerNode: DrawerNode

    fun getOrCreate(): Node {

        if (this@DrawerStateTreeHolder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        DrawerNode = DrawerNode()

        val TopBarNode = TopBarNode(
            "Home",
            Icons.Filled.Home
        ) {}

        val NavBarNode = NavBarNode()
        val PagerNode = PagerNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = TopBarNode("Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = TopBarNode("Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = TopBarNode("Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NodeItem(
                label = "Account",
                icon = Icons.Filled.Home,
                node = TopBarNode(
                    "Settings / Account",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = TopBarNode(
                    "Settings / Profile",
                    Icons.Filled.Edit
                ) {},
                selected = false
            ),
            NodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = TopBarNode(
                    "Settings / About Us",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = TopBarNode,
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setItems(navbarNavItems, 0) },
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = PagerNode.also { it.setItems(pagerNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.apply { setItems(drawerNavItems, 0) }
    }

}