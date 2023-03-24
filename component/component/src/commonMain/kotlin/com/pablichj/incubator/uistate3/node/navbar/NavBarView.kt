package com.pablichj.incubator.uistate3.node.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.NavItemDeco
import com.pablichj.incubator.uistate3.platform.LocalSafeAreaInsets

@Composable
fun NavigationBottom(
    modifier: Modifier = Modifier,
    navbarState: INavBarState,
    Content: @Composable () -> Unit
) {
    val navItems by navbarState.navItemsFlow.collectAsState(emptyList())

    Scaffold(
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
    navItems: List<NavItemDeco>,
    onNavItemClick: (NavItemDeco) -> Unit
) {
    Column {
        val safeAreaInsets = LocalSafeAreaInsets.current
        val bgColor = MaterialTheme.colors.primarySurface

        BottomNavigation(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = bgColor
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
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(safeAreaInsets.bottom.dp)
                .background(bgColor)
        )
    }
}