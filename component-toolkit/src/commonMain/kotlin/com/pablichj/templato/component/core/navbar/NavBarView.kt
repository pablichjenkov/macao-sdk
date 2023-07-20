package com.pablichj.templato.component.core.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.NavItemDeco
import com.pablichj.templato.component.platform.LocalSafeAreaInsets

@Composable
fun NavigationBottom(
    modifier: Modifier = Modifier,
    navbarState: NavBarState,
    Content: @Composable () -> Unit
) {
    val navItems by navbarState.navItemsFlow.collectAsState()

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