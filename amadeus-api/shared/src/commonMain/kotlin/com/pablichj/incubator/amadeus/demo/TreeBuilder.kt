package com.pablichj.incubator.amadeus.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.pablichj.incubator.amadeus.Database
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.setNavItems

object TreeBuilder {

    private var navBarComponent: NavBarComponent? = null

    fun getRootComponent(database: Database): Component {

        navBarComponent?.let {
            return it
        }

        return NavBarComponent().apply {
            navBarComponent = this
            setNavItems(
                mutableListOf(
                    NavItem(
                        label = "Hotel",
                        component = HotelDemoComponent(database),
                        icon = Icons.Default.Home
                    ),
                    NavItem(
                        label = "Air",
                        component = AirportDemoComponent(database),
                        icon = Icons.Default.Search
                    ),

                    ), 0
            )
        }
    }

}
