package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import com.pablichj.incubator.uistate3.node.topbar.TopBarComponent
import example.nodes.SimpleComponent

object AppBuilder {

    private lateinit var navBarComponent: NavBarComponent

    fun buildGraph(): Component {

        if (::navBarComponent.isInitialized) {
            return navBarComponent
        }

        val favoriteComponent = SimpleComponent(
            "Favorite Page",
            Color.Blue,
            {}
        )

        val settingsComponent = SimpleComponent(
            "Settings Page",
            Color.Blue,
            {}
        )

        val homeTopBarComponent = HomeComponent()
        val homeComponent = SimpleComponent(
            "Home Page",
            Color.Cyan
        ){}


        val navbarItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Default.Home,
                component = homeComponent,//homeTopBarComponent,
                selected = false
            ),
            NavItem(
                label = "Favorites",
                icon = Icons.Default.Favorite,
                component = favoriteComponent,//TopBarComponent(screenIcon = Icons.Default.Favorite),
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Default.Settings,
                component = settingsComponent,//TopBarComponent(screenIcon = Icons.Default.Settings),
                selected = false
            )
        )

        return NavBarComponent().also {
            navBarComponent = it
            it.setNavItems(navbarItems, 0)
        }

    }

}