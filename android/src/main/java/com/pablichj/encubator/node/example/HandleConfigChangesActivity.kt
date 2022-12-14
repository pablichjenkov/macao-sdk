package com.pablichj.encubator.node.example

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.nodes.AppCoordinatorNode
import com.pablichj.encubator.node.example.theme.AppTheme
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode


class HandleConfigChangesActivity : ComponentActivity() {

    // Reference to the StateTree living in the Application scope
    private lateinit var StateTree: Node

    private val childBackPressedCallback = object : com.pablichj.encubator.node.BackPressedCallback() {
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
            AppTheme {
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
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.AccountCircle) {},
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
                node = OnboardingNode(PagerNode.context, "Settings / Account", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(PagerNode.context, "Settings / Profile", Icons.Filled.Edit) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                node = OnboardingNode(PagerNode.context, "Settings / About Us", Icons.Filled.Email) {},
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
                node =  PagerNode.also { it.setNavItems(pagerNavItems, 0) },
                selected = false
            )
        )

        return DrawerNode.also { it.setNavItems(drawerNavItems, 0) }
    }


}