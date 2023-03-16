package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.example.hotelBooking.account.AccountComponent
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import example.nodes.SimpleComponent

object AppBuilder {

    private lateinit var navBarComponent: NavBarComponent

    fun buildGraph(): Component {

        if (::navBarComponent.isInitialized) {
            return navBarComponent
        }

        val homeTopBarComponent = HomeComponent()
        val favoriteComponent = SimpleComponent(
            "Favorite Page",
            Color.LightGray,
            {}
        )
        val accountComponent = AccountComponent()

        val navbarItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Default.Home,
                component = homeTopBarComponent,
            ),
            NavItem(
                label = "Favorites",
                icon = Icons.Default.Favorite,
                component = favoriteComponent,//TopBarComponent(screenIcon = Icons.Default.Favorite),
            ),
            NavItem(
                label = "Account",
                icon = Icons.Default.AccountCircle,
                component = accountComponent
            )
        )

        return NavBarComponent().also {
            navBarComponent = it
            it.setNavItems(navbarItems, 0)
        }

    }

}