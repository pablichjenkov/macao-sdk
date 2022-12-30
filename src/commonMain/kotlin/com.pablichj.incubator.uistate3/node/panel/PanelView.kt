package com.pablichj.incubator.uistate3.node.panel

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.drawer.DrawerContentList
import com.pablichj.incubator.uistate3.node.drawer.DrawerLogo

@Composable
fun NavigationPanel(
    modifier: Modifier = Modifier,
    panelState: IPanelState,
    Content: @Composable () -> Unit
) {
    val navItems by panelState.navItemsFlow.collectAsState(emptyList())

    Row(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.wrapContentWidth()
        ) {
            DrawerLogo()
            DrawerContentList(
                navItems = navItems,
                onNavItemClick = { navItem -> panelState.navItemClick(navItem) }
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Content()
        }
    }

}

