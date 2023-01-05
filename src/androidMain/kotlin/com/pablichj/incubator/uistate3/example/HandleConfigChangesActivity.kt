package com.pablichj.incubator.uistate3.example

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
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.AppCoordinatorNode
import example.nodes.OnboardingNode

class HandleConfigChangesActivity : ComponentActivity() {

    private lateinit var StateTree: Node

    private val childBackPressedCallback = object : BackPressedCallback() {
        override fun onBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootNodeContext = NodeContext.Root().apply {
            backPressDispatcher = AndroidBackPressDispatcher(
                this@HandleConfigChangesActivity
            )
            backPressedCallbackDelegate = childBackPressedCallback
        }

        StateTree = AppCoordinatorNode(rootNodeContext).also {
            it.HomeNode = buildHomeNode(it.context)
        }

        setContent {
            MaterialTheme {
                StateTree.Content(Modifier)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        StateTree.start()
    }

    override fun onStop() {
        super.onStop()
        StateTree.stop()
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

        val DrawerNode = DrawerNode(parentContext)

        val OnboardingNode = OnboardingNode(
            DrawerNode.context,
            "Home",
            Icons.Filled.Home
        ) {}
        val NavBarNode = NavBarNode(DrawerNode.context)
        val PagerNode = PagerNode(DrawerNode.context)

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.AccountCircle,
                node = OnboardingNode(
                    NavBarNode.context,
                    "Orders / Past",
                    Icons.Filled.AccountCircle
                ) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        val pagerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Account",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / Account",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / Profile",
                    Icons.Filled.Edit
                ) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = OnboardingNode(
                    PagerNode.context,
                    "Settings / About Us",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        val drawerNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode,
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                node = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = PagerNode.also { it.setNavItems(pagerNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }

}