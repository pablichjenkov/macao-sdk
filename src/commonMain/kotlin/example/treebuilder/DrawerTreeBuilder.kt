package example.treebuilder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode

object DrawerTreeBuilder {

    private lateinit var DrawerNode: DrawerNode

    fun build(
        backPressAction: () -> Unit,
    ): DrawerNode {

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            DrawerNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
                backPressAction()
            }
            return DrawerNode
        }

        val DrawerNode = DrawerNode().apply {
            context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
                backPressAction()
            }
        }

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = buildNavBarNode(),
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return DrawerNode.also { it.setItems(drawerNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarNode {

        val NavBarNode = NavBarNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Active",
                icon = Icons.Filled.Home,
                node = OnboardingNode("Orders/Active", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                node = OnboardingNode("Orders/Past", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                node = OnboardingNode("Orders/New Order", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}