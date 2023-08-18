package com.pablichj.templato.component.core.navbar

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.NavItemDeco

@Composable
fun NavigationBottom(
    modifier: Modifier = Modifier,
    navbarStatePresenter: NavBarStatePresenter,
    Content: @Composable () -> Unit
) {
    val navItems by navbarStatePresenter.navItemsState

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomBar(navItems) { navItem ->
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
    navItems: List<NavItemDeco>,
    onNavItemClick: (NavItemDeco) -> Unit
) {
    Column {
        val bgColor = MaterialTheme.colorScheme.background

        NavigationBar(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            containerColor = bgColor
        ) {
            navItems.forEach { navItem ->
                NavigationBarItem(
                    alwaysShowLabel = false,
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
}
