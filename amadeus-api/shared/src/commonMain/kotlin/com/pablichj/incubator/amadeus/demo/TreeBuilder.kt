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

    fun getRootComponent(database: Database): Component {

        return NavBarComponent().apply {
            setNavItems(
                mutableListOf(
                    NavItem(
                        label = "Hotel",
                        component = AmadeusDemoComponent(database),
                        icon = Icons.Default.Home
                    ),
                    NavItem(
                        label = "Air",
                        component = AmadeusDemoComponent(database),
                        icon = Icons.Default.Search
                    ),

                    ), 0
            )
        }

    }

}