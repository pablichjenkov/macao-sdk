package com.pablichj.incubator.uistate3.node.drawer

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
import com.pablichj.incubator.uistate3.node.NavItemDeco

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navDrawerState: INavigationDrawerState,
    content: @Composable () -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalDrawer(
        drawerContent = {
            DrawerContent(modifier, navDrawerState)
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

    LaunchedEffect(key1 = navDrawerState) {
        navDrawerState.drawerOpenFlow.collect { drawerValue ->
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
    navDrawerState: INavigationDrawerState
) {
    val navItems by navDrawerState.navItemsFlow.collectAsState(emptyList())

    Column(modifier = modifier) {
        DrawerHeader(
            drawerHeaderState = navDrawerState.drawerHeaderState
        )
        DrawerContentList(
            navItems = navItems,
            onNavItemClick = { navItem -> navDrawerState.navItemClick(navItem) }
        )
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier,
    drawerHeaderState: DrawerHeaderState
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(drawerHeaderState.style.bgColor)
            .padding(all = 16.dp),
    ) {
        Column(modifier = modifier) {
            Text(
                text = drawerHeaderState.title,
                fontSize = drawerHeaderState.style.titleTextSize
            )
            Text(
                text = drawerHeaderState.description,
                fontSize = drawerHeaderState.style.descriptionTextSize
            )
        }
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
