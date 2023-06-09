package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.pager.PagerComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

@OptIn(ExperimentalFoundationApi::class)
object PagerTreeBuilder {
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
    private lateinit var pagerComponent: PagerComponent

    fun build(): Component {

        if (this@PagerTreeBuilder::pagerComponent.isInitialized) {
            return pagerComponent
        }

        val navBarComponent1 = NavBarComponent()
        val navBarComponent2 = NavBarComponent()

        val navbarNavItems1 = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Orders/ Current", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders / Past", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Orders / Claim", StackComponent.DefaultConfig) {},
            )
        )

        val navbarNavItems2 = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Settings / Account", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Settings / Profile", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings / About Us", StackComponent.DefaultConfig) {},
            )
        )

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = navBarComponent1.also { it.setNavItems(navbarNavItems1, 0) },
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = navBarComponent2.also { it.setNavItems(navbarNavItems2, 0) },
            )
        )

        return PagerComponent(
            PagerComponent.DefaultConfig,
            diContainer
        ).also {
            pagerComponent = it
            it.setNavItems(pagerNavItems, 0)
            /*it.setPagerComponentView { modifier, childComponents ->
                Box(Modifier.fillMaxSize()) {
                    childComponents[0].Content(Modifier)
                    Text(modifier = Modifier.align(Alignment.Center),
                        text = "You should provide a Pager Composable that encloses the render of childComponents Content",
                        color = Color.Black
                    )
                }
            }*/
        }
    }

}