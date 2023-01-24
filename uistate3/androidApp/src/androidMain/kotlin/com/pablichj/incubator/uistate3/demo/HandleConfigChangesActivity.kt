package com.pablichj.incubator.uistate3.demo

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.incubator.uistate3.AndroidNodeRender
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.AppCoordinatorNode
import example.nodes.TopBarNode

class HandleConfigChangesActivity : ComponentActivity() {

    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StateTree = AppCoordinatorNode().also {
            //it.context.rootNodeBackPressedDelegate = ForwardBackPressCallback { finish() }
            it.HomeNode = buildHomeNode(it.context)
        }

        setContent {
            MaterialTheme {
                AndroidNodeRender(
                    rootNode = StateTree,
                    onBackPressEvent = { finish() }
                )
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildHomeNode(parentContext: NodeContext): Node {

        val DrawerNode = DrawerNode()

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
                icon = Icons.Filled.AccountCircle,
                node = TopBarNode("Orders / Past", Icons.Filled.AccountCircle) {},
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
                node = TopBarNode("Settings / Account", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = TopBarNode("Settings / Profile", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = TopBarNode("Settings / About Us", Icons.Filled.Email) {},
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

        return DrawerNode.also {
            it.context.attachToParent(parentContext)
            it.setItems(drawerNavItems, 0)
        }
    }

}