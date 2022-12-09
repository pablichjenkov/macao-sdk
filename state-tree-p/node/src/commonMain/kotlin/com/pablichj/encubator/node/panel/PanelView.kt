package com.pablichj.encubator.node.panel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.drawer.DrawerContentList
import com.pablichj.encubator.node.drawer.DrawerLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationPanel(
    modifier: Modifier = Modifier,
    panelState: IPanelState,
    Content: @Composable () -> Unit
) {
    val navItems by panelState.navItemsFlow.collectAsState(emptyList())

    Row(modifier = modifier.fillMaxSize()) {
        ModalDrawerSheet(
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

