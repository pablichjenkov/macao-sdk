package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

object DrawerTreeBuilder {
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
    private lateinit var drawerComponent: DrawerComponent

    @OptIn(ExperimentalLayoutApi::class)
    fun build(): DrawerComponent {

        if (DrawerTreeBuilder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings", StackComponent.DefaultConfig) {},
            )
        )

        return DrawerComponent(
            navigationDrawerState = DrawerComponent.createDefaultState(),
            config = DrawerComponent.DefaultConfig,
            diContainer = diContainer
        ).also {
            drawerComponent = it
            it.setNavItems(drawerNavItems, 0)
            /*it.setDrawerComponentView { modifier: Modifier, childComponent: Component ->
                Box(Modifier.fillMaxSize()) {
                    childComponent.Content(Modifier)
                    FlowRow {
                        navigationDrawerState.navItemsDeco.forEach {
                            Text(it.label)
                        }
                    }

                    Text(modifier = Modifier.align(Alignment.Center),
                        text = "You should provide a Composable that encloses the render of childComponent.Content(Modifier)",
                        color = Color.Black
                    )
                }
            }*/
        }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders/Active", StackComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders/Past", StackComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Orders/New Order", StackComponent.DefaultConfig) {},

                )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}