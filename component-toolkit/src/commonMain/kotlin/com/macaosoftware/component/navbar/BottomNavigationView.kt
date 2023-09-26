package com.macaosoftware.component.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun NavigationBottom(
    modifier: Modifier = Modifier,
    navbarStatePresenter: BottomNavigationStatePresenter,
    Content: @Composable () -> Unit
) {
    val navItems by navbarStatePresenter.navItemsState
    val navBarStyle = navbarStatePresenter.bottomNavigationStyle

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomBar(navItems, navBarStyle) { navItem ->
                navbarStatePresenter.navItemClick(navItem)
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
    navItems: List<BottomNavigationNavItem>,
    bottomNavigationStyle: BottomNavigationStyle,
    onNavItemClick: (BottomNavigationNavItem) -> Unit
) {
    Column {
        val bgColor = MaterialTheme.colorScheme.background

        NavigationBar(
            modifier = Modifier.fillMaxWidth().height(bottomNavigationStyle.barSize),
            containerColor = bgColor
        ) {
            navItems.forEach { navItem ->
                NavigationBarItem(
                    alwaysShowLabel = bottomNavigationStyle.showLabel,
                    selected = navItem.selected,
                    onClick = { onNavItemClick(navItem) },
                    colors = NavigationBarItemDefaults.colors(),
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
}
