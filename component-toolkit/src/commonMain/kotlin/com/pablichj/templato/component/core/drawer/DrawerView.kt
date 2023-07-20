package com.pablichj.templato.component.core.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.NavItemDeco

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navigationDrawerState: NavigationDrawerState,
    content: @Composable () -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalDrawer(
        drawerContent = {
            DrawerContent(modifier, navigationDrawerState)
        },
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = DrawerDefaults.scrimColor
    ) {
        Scaffold { paddingValues ->
            Box(modifier = modifier.fillMaxSize().padding(paddingValues)) {
                content()
            }
        }
    }

    LaunchedEffect(key1 = navigationDrawerState) {
        navigationDrawerState.drawerOpenFlow.collect { drawerValue ->
            when (drawerValue) {
                DrawerValue.Closed -> drawerState.close()
                DrawerValue.Open -> drawerState.open()
            }
        }
    }

}

@Composable
private fun DrawerContent(
    modifier: Modifier = Modifier,
    navigationDrawerState: NavigationDrawerState
) {
    val navItems by navigationDrawerState.navItemsFlow.collectAsState()
    val drawerHeaderState by navigationDrawerState.drawerHeaderState

    Column(modifier = modifier) {
        DrawerHeader(drawerHeaderState = drawerHeaderState)
        DrawerContentList(
            navItems = navItems,
            onNavItemClick = { navItem -> navigationDrawerState.navItemClick(navItem) }
        )
    }
}

@Composable
private fun DrawerContentList(
    modifier: Modifier = Modifier,
    navItems: List<NavItemDeco>,
    onNavItemClick: (NavItemDeco) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (navItem in navItems) {
            NavigationDrawerItem(
                label = { Text(navItem.label) },
                icon = { Icon(navItem.icon, null) },
                selected = navItem.selected,
                onClick = { onNavItemClick(navItem) }
            )
        }
    }
}

@Composable
fun NavigationDrawerItem(
    label: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    val modifier = if (selected) {
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(width = 1.dp, color = Color.Black)
            .background(Color.LightGray)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    } else {
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        label()
    }
}
