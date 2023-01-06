package com.pablichj.incubator.uistate3.node.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pablichj.incubator.uistate3.node.NodeItem

@Composable
fun NavigationBottom(
    modifier: Modifier = Modifier,
    navbarState: INavBarState,
    Content: @Composable () -> Unit
) {
    val navItems by navbarState.navItemsFlow.collectAsState(emptyList())

    Scaffold (
        modifier = modifier,
        bottomBar = {
            BottomBar(navItems) { navItem ->
                navbarState.navItemClick(navItem)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Content()
        }
    }

}

@Composable
private fun BottomBar(
    navItems: List<NodeItem>,
    onNavItemClick: (NodeItem) -> Unit
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth()
    ) {
        navItems.forEach { navItem ->
            BottomNavigationItem(
                label = { Text(text = navItem.label) },
                alwaysShowLabel = true,
                selected = navItem.selected,
                onClick = { onNavItemClick(navItem) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                }
            )
        }
    }
}